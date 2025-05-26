# Job-Swap
This is a 2nd Semester Computer Science project about a Job-Swap application.
This application allows employees to switch jobs permanently with other employees in order to work closer to where they live.

This project contains the first interation of the project with 2 more expected "in theory" for the program to be finished.

### Case
More than 1.2 million danes commutes to and from work every day.
This causes large socioeconomic and personal costs. 
A proposed solution to this is a Job-swap between two employees that have the same position and lives close to
each others workplaces.

##### Problem
* How can a possible Job-swap happen?
* How can a relationship between employees be made?
* How would such a solution be implemented?

## Solution
#### Tools
Database: Microsoft SQL
IDE: IntelliJ
Models Mermaid.live and Draw.io
Version Control: GitHub.com

Languages for the project.
* Java 
* T-SQL 
* SQL
* English

#### Glossary
Match       - A Match as different states but a match is when a user has 
requested another user.

| MatchState                    | Description                                                         |
|-------------------------------|---------------------------------------------------------------------|
| Requested                     | When another user has requested an owned users job                  |
| Application                   | When a owner user has requested another user                        |
| Interested                    | When both users are interested in switching (This enables Messages) |
| Match                         | When both users want to match/switch job                            |
| One profile is ready to match | A single profile wants to match                                     |
| Accepted Match                | HR has accepted the match                                           |


## Sequence Diagrams

#### Request Match
```mermaid
sequenceDiagram
    actor User
    participant MainSceneController as :MainSceneController
    participant UserTabSeekJobSwap as UserTabMatches:Tab
    participant MatchDB as MatchDB:MatchDBInterface
    participant UserTabMatches as userTabMatches:Tab
    participant DBConnection as :DBConnection

    %% User logs in and sees main menu
    User->>+MainSceneController: initialize()

    %% User clicks "Seek JobSwap" tab
    User->>+UserTabSeekJobSwap: clicks on SeekJobSwap Tab

    %% UserTabSeekJobSwap shows job swap options
    UserTabSeekJobSwap->>UserTabSeekJobSwap: showJobswapOptions()

    %% User selects department(s)
    User->>UserTabSeekJobSwap: updateJobList()

    %% UserTabSeekJobSwap fetches possible matches
    UserTabSeekJobSwap->>+MatchDB: seekAllPossibleProfileMatches(profileID, wantedDepartment)
    MatchDB->>+DBConnection: getConnection()
    MatchDB-->>-UserTabSeekJobSwap: List<Profile>

    %% User sees possible matches and selects one
    User->>UserTabSeekJobSwap: applyForJobswap(profileToApplyTo)

    %% UserTabSeekJobSwap creates a new Match
    UserTabSeekJobSwap->>+MatchDB: createMatch(newMatch)
    MatchDB->>DBConnection: getConnection()
    deactivate MatchDB

    %% UserTabSeekJobSwap updates UI
    UserTabSeekJobSwap->>UserTabSeekJobSwap: updateJobList()

    %% UserTabSeekJobSwap refreshes matches tab
    UserTabSeekJobSwap->>+UserTabMatches: refreshMatchDisplay()


    %% End of interaction
    deactivate UserTabMatches 
    deactivate UserTabSeekJobSwap
    deactivate MainSceneController
    deactivate DBConnection
```

#### Login as User
```mermaid
sequenceDiagram
    actor User
    participant LC as :LoginController
    participant LDB as LoginDB:LoginDBInterface
    participant PSW as :PasswordEncrypter
    participant PDB as ProfileDB:ProfileDBInterface
    participant SS as :SceneService
    participant FXML as :FXMLLoader
    participant A as :Alert
    participant DBC as :DBConnection
    participant MS as :MainSceneController

    User->>+LC: open login page
    User->>LC: enter ProfileID
    User->>LC: enter Password
    User->>LC: click loginButton
    LC->>PSW: Encrypt(pass)
    PSW-->>LC: encryptedPass
    LC->>+LDB: checkCredentials(id, pass)
    LDB->>+DBC: getConnection()
    LDB-->>-LC: boolean (valid/invalid)

    alt Login success
        LC->>+PDB: getProfileFromID(id)
        PDB->>DBC: getConnection()
        PDB-->>-LC: Profile
        LC->>MS: setCurrentProfile(profile)
        LC->>SS: shiftScene(event, "Jobswap", "/org/example/jobswap/MainScene.fxml")
        SS->>+FXML: load()
        FXML->>+MS: initialize()
        deactivate FXML
        deactivate LC
        deactivate MS
        
    else Login failed
        activate LC
        LC->>+A: show error ("Wrong Credentials")
        User->>A: Close error A
        deactivate A
        
        User->>LC: Retries
        loop Max 5 attempts
            User->>LC: enter ProfileID/Password
            LC->>PSW: Encrypt(pass)
            PSW-->>LC: encryptedPass
            LC->>+LDB: checkCredentials(id, pass)
            LDB->>DBC: getConnection()
            LDB-->>-LC: boolean (valid/invalid)
            alt Login success
                
            else Login failed
                LC->>+A: show error ("Wrong Credentials")
                User->>A: Close error A
                deactivate A
            end
        end
        alt 5 failed attempts
            User->>LC: Cannot log in
            Note over User,LC: Profile is locked (handled by admin, UC33)
        end
    end
    deactivate DBC
    deactivate LC
```

#### Create User
```mermaid
sequenceDiagram
    actor User
    participant LC as :LoginController
    participant SS as :SceneService
    participant NPC as NewProfileController
    participant PSW as :PasswordEncrypter
    participant P as :Profile
    participant PDB as ProfileDB:ProfileDBInterface
    participant LDB as LoginDB:LoginDBInterface
    participant DBC as :DBConnection

    %% User starts the process
    User->>+LC: SceneShiftToCreateAccount()
    LC->>+SS: shiftScene()
    
    deactivate LC
    SS->>+NPC: show()
    deactivate SS

    %% User fills in fields and clicks 'Create'
    User->>NPC: Fill form
    User->>NPC: createProfileInDatabase()

    %% NPC collects data and encrypts password
    NPC->>PSW: encrypt(password)

    %% NPC creates P object
    NPC-->>+P: <<create>>
    P-->>NPC: Profile instance

    %% NPC calls PDB to create P in DB
    NPC->>+PDB: createNewProfile(profile)
    PDB->>+DBC: getConnection()
    PDB-->>-NPC: Return success/failure

    %% If success, NPC creates login in LDB
    alt Profile created successfully
        NPC->>+LDB: addLoginToDataBase(ProfileID, encryptedPassword)
        LDB->>DBC: getConnection()
        LDB-->>-NPC: Return success
        %% Scene changes to Login
        NPC->>+SS: shiftScene(actionEvent, "Login Screen", "/org/example/jobswap/Login.fxml")
        deactivate NPC
        deactivate P
        SS-->>+LC: initialize()
        deactivate SS
        LC->>User: Can login with new Profile
    end

    deactivate LC
    deactivate DBC
```