package api;

public class Status {
    /* Generic */
    public static Integer OK = 200;
    public static Integer BAD_REQUEST = 400;
    public static Integer UNAUTHORIZED = 401;
    public static Integer INTERNAL_ERROR = 500;
    /* Contest => Status */
    public static Integer PRESENTATIONS_OPENED = 601;
    public static Integer VOTES_OPENED = 602;
    public static Integer CONTEST_CLOSED = 603;
    /* Contest => Response to Frontend */
    public static Integer CONTEST_OPENED = 600;
    public static Integer CONTEST_NOT_CREATED = 604;
    public static Integer USER_HAS_ALREADY_VOTED = 605;
    public static Integer USER_HAS_NOT_VOTED = 606;
    public static Integer VOTES_CLOSED = 607;
    public static Integer PRESENTATIONS_CLOSED = 608;
    public static Integer NOT_ENOUGH_PARTICIPANTS = 609;
    public static Integer PHOTOS_UPLOADED_AND_USERS_ARE_DIFFERENT = 610;
    public static Integer USER_IS_NOT_ADMIN = 611;
    public static Integer STATUS_COULD_NOT_BE_UPDATED = 612;
    public static Integer STATUS_NOT_CORRECT = 613;
    public static Integer CANNOT_INSERT_RESULTS = 614;
    public static Integer CANNOT_CLOSE_CONTEST = 615;
    public static Integer MORE_VOTES_THAN_CENSUS = 616;
    public static Integer CANNOT_SHOW_RESULTS = 617;
    /* Photos */
    public static Integer TITLE_REQUIRED = 701;
    public static Integer DESCRIPTION_REQUIRED = 702;
    public static Integer FILENAME_REQUIRED = 703;
    public static Integer USER_HAS_ALLREADY_UPLOADED_PHOTO = 704;
    public static Integer USER_HAS_NOT_UPLOADED_PHOTO = 705;
    public static Integer PHOTO_CANNOT_BE_UPLOADED = 706;
    public static Integer WRONG_METADATA = 707;
    /* Votes */
    public static Integer VOTE_CANNOT_BE_SUBMITTED = 800;
    public static Integer USER_TRY_VOTE_PHOTO_NOT_LISTED = 801;
    public static Integer VOTES_DECRYPTED_WRONG_SIZE = 802;
}
