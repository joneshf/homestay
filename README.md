homestay
========

* CreateAccount
  * Email
  * Password
  * First/Last Name
  * Validate & Create
    * Email is unique
    * Password is at least 8 characters, 1 number, 1 Uppercase and 1 lowercase
    * Both names are non-empty
  * Move to main menu
* SignIn
  * Email
  * Password
  * Validate
    * Wrong password -> Show error
    * Wrong five times -> lockout for 5 minutes
* ForgotPassword
  * Enter email
  * Send reset token
* HostScreen
  * Edit Host info
    * Preferences/Settings
  * View Postings
    * Update
      * Edit posting info
      * Change availability
    * Delete
  * New posting
    * Set available dates
    * Actual address or area
    * Room size
    * Cost
* StudentScreen
  * Search
    * Select preferences
    * Save searches
    * Recall old searches
    * View posting
    * Submit for approval
* Profile
  * Name
  * Photo
  * Email
  * Phone number
  * Street Address
* GroupChat
  * View chat
    * Add message
    * Approve/disapprove
    * Delete chat
    * Flag innappropriate
* AdminScreen
  * View request for chat
    * Create chat with host
      * Send message to both
  * View flagged chats
    * Block communication
    * Remove flag
