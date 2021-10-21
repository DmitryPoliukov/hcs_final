package by.epamtc.poliukov.dao;

public final class ColumnName {
    //final class singleton

    // roles
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_NAME = "role_name";

    //users
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "login";
    public static final String LOGIN = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String SECOND_NAME = "second_name";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String AVATAR = "avatar";
    public static final String USER_ROLE_ID = "role_id_fk";

    //tenant
    public static final String TENANT = "tenant";
    public static final String TENANT_ID = "part_user_id";
    public static final String CITY = "city";
    public static final String ADDRESS = "address";

    //employee
    public static final String EMPLOYEE = "employee";
    public static final String EMPLOYEE_ID = "part_user_id";
    public static final String VALUE_PERSON_HOUR = "value_person_hour";
    public static final String RATING = "rating";
    public static final String NUMBERS_OF_FEEDBACK = "numbers_of_feedback";
    public static final String INFORMATION = "information";
    public static final String IS_BLOCKED = "is_blocked";

    //work type
    public static final String WORK_TYPE_ID = "work_type_id";
    public static final String WORK_TYPE_NAME = "work_type_name";

    //work request and subquery
    public static final String REQUEST_ID = "request_id";
    public static final String FILLING_DATE = "filling_date";
    public static final String PLANNED_DATE = "planned_date";
    public static final String TENANT_USER_ID_FK = "tenant_user_id_fk";
    public static final String REQUEST_STATUS_ID_FK = "request_status_id_fk";
    public static final String SUB_ID = "sub_id";
    public static final String REQUEST_STATUS_ID = "request_status_id";
    public static final String REQUEST_STATUS_NAME = "request_status_name";
    public static final String EMPLOYEE_USER_ID_FK = "employee_user_id_fk";
    public static final String WORKS_REQUEST_ID_FK = "works_request_id_fk";
    public static final String COMPLETION_DATE = "completion_date";


    public static final String AMOUNT_OF_WORK_IN_HOURS = "amount_of_work_in_hours";
    public static final String SUB_WORK_REQUEST_ID_FK = "sub_work_request_id_fk";
    public static final String SUB_WORK_TYPE_ID = "sub_work_type_id";

}
