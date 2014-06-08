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
	/* Photos */
	public static Integer TITLE_REQUIRED = 402;
	public static Integer DESCRIPTION_REQUIRED = 403;
	public static Integer FILENAME_REQUIRED = 404;
	public static Integer USER_ALLREADY_HAS_IMAGE = 405;
	public static Integer USER_HAS_NOT_PHOTO = 406;
}
