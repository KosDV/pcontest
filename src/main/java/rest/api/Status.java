package rest.api;

public class Status {
    /* Generic */
    public static Integer OK = 200;
    public static Integer BAD_REQUEST = 400;
    public static Integer UNAUTHORIZED = 401;
    public static Integer INTERNAL_ERROR = 500;
    /* Contest */
    public static Integer CONTEST_OPENED = 600;
    public static Integer PRESENTATIONS_OPENED = 601;
    public static Integer VOTES_OPENED = 602;
    public static Integer CONTEST_CLOSED = 603;
    public static Integer CONTEST_NOT_CREATED = 604;
    public static Integer USER_HAS_ALREADY_VOTED = 605;
    public static Integer USER_HAS_NOT_VOTED = 606;
    public static Integer VOTES_CLOSED = 607;
    public static Integer PRESENTATIONS_CLOSED = 608;
    public static Integer NOT_ENOUGH_PARTICIPANTS = 609;
    public static Integer PHOTOS_UPLOADED_AND_USERS_DIFFERENT = 610;
    /* Photos */
    public static Integer TITLE_REQUIRED = 701;
    public static Integer DESCRIPTION_REQUIRED = 702;
    public static Integer FILENAME_REQUIRED = 703;
    public static Integer USER_ALLREADY_HAS_IMAGE = 704;
    public static Integer USER_HAS_NOT_UPLOADED_PHOTO = 705;
    public static Integer PHOTO_CANNOT_BE_UPLOADED = 706;
    /* Votes */
    public static Integer VOTE_CANNOT_BE_SUBMITTED = 800;
    public static Integer VOTE_WITHOUT_PHOTO_LIST = 801;

}
