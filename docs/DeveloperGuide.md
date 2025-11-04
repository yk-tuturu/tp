---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Parent Connect Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

original source: [AddressBook-Level3](https://se-education.org/addressbook-level3/DeveloperGuide.html)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/tree/master/src/main) and [`MainApp`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a child).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="700" />


The `Model` component,

* stores the app's data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both app's data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both AddressBookStorage and UserPrefStorage, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the Model component (because the Storage component’s job is to save/retrieve objects that belong to the Model)
* validates persisted data on read and surface clear errors or recovery actions when files are missing or corrupted.
* addressbook.json contains 2 sections `persons` and `subjectScores`. Each score entry in `subjectScores` has a stable link to the person achieved through an internal map from name to id. Refer to typical JSON data file [here](https://github.com/AY2526S1-CS2103T-F08a-4/tp/blob/master/src/test/data/JsonSerializableAddressBookTest/typicalPersonsAddressBook.json)

Read / write flow (high level)

- Write (save): convert in‑memory ScoreEntry and Person objects to JSON DTOs and write them to the configured data file(s).
- Read (load): read persons first and reconstruct the in‑memory AddressBook. Then read the `subjectScores` section and convert each DTO into a domain ScoreEntry while resolving each entry's link to a Person in the loaded AddressBook.
- Validation happens during DTO→domain conversion. Any malformed or semantically invalid fields should produce clear, testable errors.

- DTOs (the JSON adapter classes) map raw JSON to typed fields and perform syntactic validation (presence, types, basic formats). They convert to domain objects via a conversion method (e.g., `toModelType()`), which throws a clear exception on invalid data.

Validation rules (examples to document)
- Subject: non‑empty identifier (or valid enum value if subjects are fixed by product policy).
- Score: numeric and within project bounds (e.g., 0..100). Reject out‑of‑range values with a clear message.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## Implementation

This section describes some noteworthy details on how certain systems are implemented. 

### Subject System

The subject system manages student enrollment and academic scores through three main components:
- `Subject` (enum): Represents fixed subjects (MATH, ENGLISH, SCIENCE)
- `ScoreDict`: Manages score records for each subject
- `SubjectRegistry`: Provides centralized access to enrollment and score data

#### Core Classes

**Class Diagram:**
<puml src="diagrams/SubjectClassDiagram.puml" width="300" />

#### Implementation Details

The subject system implements enrollment and scoring functionality as follows:

1. **Enroll Command Flow**

**How it works:**
- Upon execution, command checks for the validity of its inputs, such that the subject and indexes provided is valid
- For every person and every subject listed in the command ,it calls `Subject::enrollPerson()`
- The person is added to the list of persons enrolled in the subject
- The subject updates its corresponding `ScoreDict` with a default score of `-1`

<puml src="diagrams/SubjectEnrollSequenceDiagram.puml" width="700" />

2. **Unenroll Command Flow**

**How it works:**
- Upon execution, command checks for the validity of its inputs, such that the subject and indexes provided is valid
- For every person and every subject listed in the command ,it calls `Subject::unenrollPerson()`
- The person is removed from the list of persons enrolled in the subject
- The subject removes the person entry from its corresponding `ScoreDict`

<puml src="diagrams/SubjectUnenrollSequenceDiagram.puml" width="700" />


3. **Set Score Command Flow**

**How it works:**
- Upon execution, command checks for the validity of its inputs, such that the subject and indexes provided is valid
- For every person and every subject listed in the command ,it calls `Subject::setScore()`
- The subject checks if the person provided is enrolled in the corresponding subject, if not, it throws an error which is captured gracefully by the command object
- If the person is enrolled, set the relevant entry in `ScoreDict` to the provided value.

<puml src="diagrams/SubjectScoreSequenceDiagram.puml" width="700" />

#### Design Considerations

##### Aspect: Score Storage
* **Alternative 1 (current choice):** Store scores in `ScoreDict` within each `Subject`
    * Pros: Direct access to scores through subject
    * Cons: Score data distributed across subjects
* **Alternative 2:** Subject and Score storage within `Person` object
    * Pros: Single source of truth for both person information and subject information, as they are all packaged within `Person`
    * Cons: More complex enrollment data lookup and management

##### Aspect: Subject Representation
* **Alternative 1 (current choice):** Use enum for subjects
    * Pros:
        * Type-safe subject references
        * Built-in singleton behavior
        * Compile-time validation of subject types
    * Cons:
        * Cannot add new subjects without code changes
* **Alternative 2:** Dynamic subject creation
    * Pros: Flexible subject addition at runtime
    * Cons: Less type safety, more complex validation

#### Notable Features

1. **Observable Collections**
- Uses JavaFX `ObservableMap` for UI binding
- Automatic UI updates when scores change
- Thread-safe score modifications

2. **Validation Rules**
- Score range: -1 (default) or 0-100
- Enrollment check before score assignment
- Case-insensitive subject name matching

3. **Data Access Control**
- Read-only views of internal collections
- Controlled mutation through public methods
- Clear error messages for invalid operations

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* kindergarten teacher/admin
* has to manage a significant number of children
* needs to keep track of the personal details and parents’ contact information
* would like to manage the child’s activities and preferences (diet restrictions, grades, attendance)
* prefer desktop apps over other types
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

* Provide fast access to the parents' contact details of any given child
* Provide functionality to record and access the details of each child (e.g. allergies, preferences, grades etc.)



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| ID | Priority | Epic                              | As a …​              | I want to …​                                            | So that I can…​                                                  |
|----|----------|-----------------------------------|----------------------|---------------------------------------------------------|------------------------------------------------------------------|
| C1 | `* * *`  | Contact Management                | kindergarten teacher | view a child's parents' contact details                 | quickly contact them during emergencies                          | 
| C2 | `* * *`  | Contact Management                | kindergarten admin   | store and retrieve parents' phone numbers and addresses | send out updates and notices efficiently                         |
| C3 | `* * *`  | Contact Management                | kindergarten teacher | search for a child by name                              | access their profile quickly                                     | 
| C4 | `* *`    | Contact Management                | kindergarten admin   | see the name of the primary guardian                    | know who to contact first                                        |
| C5 | `* *`    | Contact Management                | kindergarten teacher | update a parent's phone number                          | ensure the system always reflects the latest contact information |
| P1 | `* * *`  | Child Profiling                   | kindergarten teacher | record important facts about each child                 | tailor care and instructions to their needs                      |
| P2 | `* * *`  | Child Profiling                   | kindergarten teacher | record emergency care instructions                      | act quickly in case of an incident                               |
| U1 | `* *`    | System Usability & Data Integrity | kindergarten teacher | delete outdated child information                       | ensure the database remains accurate                             |
| U2 | `* *`    | System Usability & Data Integrity | kindergarten teacher | edit outdated child information                         | ensure the database remains accurate                             |
| A1 | `* *`    | Academic Tracking                 | kindergarten teacher | record test scores for each child                       | track their progress over time                                   |
| A2 | `* *`    | Academic Tracking                 | kindergarten teacher | tag notes alongside attendance                          | ensure context is preserved in records                           |
| A3 | `* *`    | Academic Tracking                 | kindergarten teacher | bulk delete child records                               |                                                                  |
| A4 | `* *`    | Academic Tracking                 | kindergarten teacher | bulk enroll/unenroll children                           |                                                                  |
| A5 | `* *`    | Academic Tracking                 | kindergarten teacher | enroll/unenroll specified children into subjects        |                                                                  |
| F1 | `* *`    | Filtering and Grouping            | kindergarten teacher | filter children by food allergies                       | inform the kitchen staff accordingly                             |
| F2 | `* *`    | Filtering and Grouping            | kindergarten teacher | filter children with special needs                      | prepare inclusive activities                                     |
| F3 | `*`      | Filtering and Grouping            | kindergarten teacher | filter children who are on medication                   | remind staff to monitor them more closely                        |



### Use cases

(For all use cases below, the **System** is the `ParentConnect` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - View all child records**

**MSS**

1.  User requests to view all child records.
2.  ParentConnect displays a list of all child records.

   Use case ends.

**Extensions**

* 1a. There are no existing child records.

  Use case ends.

**Use case: UC02 - Add a child record**

**MSS**

1.  User requests to add a child record and provides the necessary details.
2.  ParentConnect adds the child record and displays details of the newly added child record.
3.  ParentConnect displays a list of all child records.

   Use case ends.

**Extensions**

* 1a. The provided details are invalid.

    * 1a1. ParentConnect shows an error message.

      Use case ends.

* 1b. Some necessary details are not provided by user.

    * 1b1. ParentConnect shows an error message.
  
      Use case ends.

* 1c. The provided details match an existing child record.

    * 1c1. ParentConnect shows an error message.
  
      Use case ends.


**Use case: UC03 - Delete child record(s)**

**MSS**

1.  User requests to delete child record(s) in the displayed list.
2.  ParentConnect deletes the child record(s) and displays details of the deleted child record.
3.  ParentConnect updates the displayed child records.

    Use case ends.

**Extensions**

* 1a. The displayed list is currently empty or provided index(es) is invalid.

    * 1a1. ParentConnect shows an error message.
  
      Use case ends.

* 1b. No index is provided.

    * 1b1. ParentConnect shows an error message.

      Use case ends.

**Use case: UC04 - Edit a child record**

**MSS**

1.  User requests to edit a specific child record in the displayed list and provides necessary details.
2.  ParentConnect updates the child record with the new info provided.
3.  ParentConnect updates the displayed child records.

    Use case ends.

**Extensions**

* 1a. The displayed list is empty or the provided index is invalid.

    * 1a1. ParentConnect shows an error message.
  
      Use case ends.

* 1b. The provided details are invalid.

    * 1b1. ParentConnect shows an error message.
  
      Use case ends.

* 1c. No details are provided.

    * 1c1. ParentConnect shows an error message.
  
      Use case ends.

**Use case: UC05 - Find child records**

**MSS**

1.  User enters search command and provides search details.
2.  ParentConnect displays a list of child records based on search details.

    Use case ends.

**Extensions**

* 1a. The provided details are invalid.

    * 1a1. ParentConnect shows an error message.
  
      Use case ends.

* 1b. The provided details are missing.

    * 1b1. ParentConnect shows an error message.

      Use case ends.

**Use case: UC06 - Clear child records**

**MSS**

1.  User requests to clear all child records.
2.  ParentConnect clears all child records.
3.  ParentConnect displays an empty list.

    Use case ends.

**Use case: UC07 - Enroll child(ren) into subject(s)**

**MSS**

1.  User requests to enroll the selected child(ren) into the selected subject(s).
2.  ParentConnect enrolls the selected child(ren) into the selected subject(s).
3.  ParentConnect updates the displayed child records.

    Use case ends.

**Extensions**

* 1a. The provided details are invalid.

    * 1a1. ParentConnect shows an error message.

      Use case ends.

* 1b. The provided details are missing.

    * 1b1. ParentConnect shows an error message.

      Use case ends.

* 1c. The selected child(ren) is/are already enrolled in the selected subjects.

    * 1c1. ParentConnect shows a message informing the user.

      Use case ends.

**Use case: UC08 - Unenroll child(ren) from subject(s)**

**MSS**

1.  User requests to unenroll the selected child(ren) from the selected subject(s).
2.  ParentConnect unenrolls the selected child(ren) from the selected subject(s).
3.  ParentConnect updates the displayed child records.

    Use case ends.

**Extensions**

* 1a. The provided details are invalid.

    * 1a1. ParentConnect shows an error message.

      Use case ends.

* 1b. The provided details are missing.

    * 1b1. ParentConnect shows an error message.

      Use case ends.

* 1c. The selected child(ren) is/are not enrolled in the selected subjects.

    * 1c1. ParentConnect shows a message informing the user.

      Use case ends.

**Use case: UC09 - Set score of child(ren) for subject**

**Guarantees**<br>
- Selected children will only have score updated if they are enrolled in the selected subject.
- Children not enrolled in the subject will not have their records affected in any way through this action.

**MSS**

1.  User requests to set the score of the selected child(ren) for the selected subject.
2.  ParentConnect sets the score of the selected child(ren) for the selected subject.
3.  ParentConnect updates the displayed child records.

    Use case ends.

**Extensions**

* 1a. The provided details are invalid.

    * 1a1. ParentConnect shows an error message.

      Use case ends.

* 1b. The provided details are missing.

    * 1b1. ParentConnect shows an error message.

      Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 100 child records without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should have a special colouring scheme to differentiate between tags, allergies and subject fields.
5.  Should warn the user if their data file is corrupted on startup.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Child**: A child enrolled in the kindergarten
* **User**: Kindergarten teacher or admin who uses ParentConnect to manage child records
* **Parent**: A guardian of a child (e.g., parent, legal guardian, caretaker)
* **Contact**: Information that allows one to contact a parent (e.g., phone number, address)
* **DTO**: Data Transfer Object, an object that carries data between processes
* **CLI**: Command Line Interface, a text-based user interface used to operate software
* **GUI**: Graphical User Interface, a visual-based user interface used to operate software
* **Datafile**: A file on the hard disk where ParentConnect stores its data, a json file

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Finding a child record

1. Finding a child record while some child records are being shown

    1. Prerequisites: Sample data provided upon initialisation is used.

    1. Test case: `find c/Li David`<br>
       Expected: Child records for `David Li` and `Charlotte Oliveir` are displayed. Number of child records found is shown in the status message.

    1. Test case: `find t/adhd`<br>
       Expected: Child records for `Alex Jr` and `Bernice Yu` are displayed. Number of child records found is shown in the status message.

    1. Test case: `find a/City`<br>
       Expected: Displayed records will not change. Error details shown in the status message.

    1. Other incorrect find commands to try: `find`, `find c/Li c/David` <br>
       Expected: Similar to previous.

### Adding a child record

1. Adding a child record

    1. Test case: `add c/Hua Cheng b/Xie Lian a/Paradise Mansion p/66661111 e/xielian@example.com`<br>
       Expected: Child record is added to the list. Details of the added child record shown in the status message. Displayed child records are updated.

    1. Test case: `add c/Hua Cheng b/Xie Lian a/Paradise Mansion p/66661111 e/xielian@example.com`<br>
       Expected: If this is done again after step 1., an error message will be shown in the status message due to detected duplicate. No child record is added.

    1. Test case: `add c/Nyx Xia b/Selene Yan a/Ocean City p/88888888 e/selene@example.com r/cats t/ocd`<br>
       Expected: Child record is added to the list. Details of the added child record shown in the status message. Displayed child records are updated.

    1. Test case: `add c/inval!d N@me`<br>
       Expected: No child record is added. Error details shown in the status message.

    1. Other incorrect add commands to try: `add`, `add e/invalid_email`, `add c/Nyx Xia b/Selene Yan a/Ocean City p/88888888` <br>
       Expected: Similar to previous.

### Deleting child record(s)

1. Deleting child record(s) while some child records are being shown

    1. Prerequisites: At least three child records in the displayed list.

    1. Test case: `delete 1`<br>
       Expected: First child record is deleted from the list. Details of the deleted child record shown in the status message.

    1. Test case: `delete 1 2`<br>
       Expected: The first and second child records are both deleted from the list. Details of the deleted child records shown in the status message.

    1. Test case: `delete 0`<br>
       Expected: No child record is deleted. Error details shown in the status message.

    1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Editing a child record

1. Editing a child record while some child records are being shown

    1. Prerequisites: Multiple child records in the list.

    1. Test case: `edit 1 p/12345678 a/123, Nice Nature Road`<br>
       Expected: First child record's parent phone number and address are updated. Details of the updated record are shown in the status message.

    1. Test case: `edit 0`<br>
       Expected: No record is updated. Error details shown in the status message.

    1. Other incorrect edit commands to try: `edit`, `edit 1`, `edit p/1234` <br>
       Expected: Similar to previous.

### Enrolling a child into a subject

1. Enrolling a child into a subject while some child records are being shown

    1. Prerequisites: Multiple child records are being displayed in the list.

    1. Test case: `enroll 1 2 s/science s/math s/english`<br>
       Expected: First two child records' subjects are updated. Details of the enrollment are shown in the status message.

    1. Test case: `enroll all s/math`<br>
       Expected: All displayed child records' subjects are updated. Details of the enrollment are shown in the status message.
                 If a child is already enrolled in the subject before running this command, they will be skipped. This is reflected in the status message.
                 If all the displayed children are already enrolled in the subject before running this command, the status message will inform the user about this.

    1. Test case: `enroll 1`<br>
       Expected: No record is updated. Error details shown in the status message.

    1. Other incorrect enroll commands to try: `enroll`, `enroll 0 s/math` <br>
       Expected: Similar to previous.

### Setting the score of a child for a subject

1. Setting the score of a child for a subject while some child records are being shown

    1. Prerequisites: Multiple child records are being displayed in the list. The following tests are done right after the manual testing for enrollment above.

    1. Test case: `setscore 1 2 s/science g/50`<br>
       Expected: First two child records' scores are updated. Details of the update are shown in the status message.

    1. Test case: `setscore all s/math g/20`<br>
       Expected: All displayed child records' subjects are updated. Details of the update are shown in the status message.

    1. Test case: `setscore 1 2 s/science s/math g/50`<br>
       Expected: No record is updated. Error details shown in the status message.

    1. Other incorrect set score commands to try: `setscore`, `setscore 1 s/science g/50 g/20`, `setscore 1` <br>
       Expected: Similar to previous.

### Unenrolling a child from a subject

1. Unenrolling a child from a subject while some child records are being shown

    1. Prerequisites: Multiple child records are being displayed in the list.
       The following tests are done after the manual testing for enrollment above.

    1. Test case: `unenroll 1 2 s/science s/math s/english`<br>
       Expected: First two child records' subjects are updated. Details of the unenrollment are shown in the status message.

    1. Test case: `unenroll all s/math`<br>
       Expected: All displayed child records' subjects are updated. Details of the unenrollment are shown in the status message.
       If a child is not enrolled in the subject before running this command, they will be skipped. This is reflected in the status message.
       If all the displayed children are not enrolled in the subject before running this command, the status message will inform the user about this.

    1. Test case: `unenroll 1`<br>
       Expected: No record is updated. Error details shown in the status message.

    1. Other incorrect unenroll commands to try: `unenroll`, `unenroll 0 s/math` <br>
       Expected: Similar to previous.

### Saving data
1. Automatic data saving

    1.1. **Test automatic save after add command:**
      - Prerequisites: Database does not have an entry where child's name is "Test Child" and parent's name is "Test Parent".
      - Execute `add c/Test Child b/Test Parent a/Test Address p/12345678 e/test@example.com`.
      - Exit the program.
      - Navigate to the directory where you ran `java -jar parentconnect.jar`.
      - Open data/parentconnect.json in a text editor.
      - Search for the newly added child's name (or parent's name).
      - Expected: the newly added child is present in the json file.

    1.2. **Test automatic save after edit command:**
      - Prerequisites: At least one child record exists.
      - Execute `edit 1 p/87654321`.
      - Exit the program.
      - Navigate to the directory where you ran `java -jar parentconnect.jar`.
      - Open data/parentconnect.json in a text editor.
      - Expected: The edited phone number is reflected in the JSON file.

    1.3. **Test automatic save after delete command:**
      - Prerequisites: At least one child record exists.
      - Note the first child's name.
      - Execute `delete 1`.
      - Exit the program.
      - Navigate to the directory where you ran `java -jar parentconnect.jar`.
      - Open data/parentconnect.json in a text editor.
      - Open data/parentconnect.json in a text editor.
      - Expected: The deleted child record is no longer present in the JSON file.

    1.4. **Test automatic save after enroll command:**
      - Prerequisites: At least one child record exists.
      - Execute `enroll 1 s/math`.
      - Exit the program.
      - Navigate to the directory where you ran `java -jar parentconnect.jar`.
      - Expected: The child's name is present in MATH subject.

    1.5. **Test automatic save after setscore command:**
      - Prerequisites: The first child is enrolled in math.
      - Execute `setscore 1 s/math g/85`.
      - Exit the program.
      - Navigate to the directory where you ran `java -jar parentconnect.jar`.
      - Open data/parentconnect.json in a text editor.
      - Expected: The `subjectScores` section contains an entry with the child's name, subject "math", and score 85.
