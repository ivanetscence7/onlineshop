package net.thumbtack.onlineshop.exception;

public enum AppErrorCode {

    SUCCESS("", ""),
    WRONG_TOKEN("Wrong token", "token"),
    WRONG_LOGIN("Login doesn't match", "login"),
    LOGIN_ALREADY_EXISTS("User %s already exists", "login"),
    WRONG_PASSWORD("Password doesn't match", "password"),
    WRONG_FIRSTNAME("FirstName doesn't match", "firstName"),
    WRONG_LASTNAME("LastName doesn't match", "lastName"),
    WRONG_PATRONYMIC("Patronymic doesn't match", "patronymic"),
    WRONG_POSITION("Position doesn't match", "position"),
    WRONG_EMAIL("Email doesn't match", "email"),
    WRONG_ADDRESS("Address doesn't", "address"),
    WRONG_PHONENUMBER("Phone number doesn't match", "phone"),
    PERMISSION_DENIED("You do not have enough rights", "token"),
    USER_NOT_FOUND("Cannot determine user", "token"),
    SUBCATEGORY_ERROR("A category cannot become a subcategory of a subcategory", "parentId"),
    UPDATE_CATEGORY_ERROR_PARENTID("Main category can't become a subcategory", "parentId"),
    UPDATE_CATEGORY_ERROR_PARENTID_EQUALS_ID("Category can't become subcategory for yourself", "parentId"),
    NO_SUCH_CATEGORY_BY_ID("No such category by id = %s", "id"),
    NO_SUCH_CATEGORY_BY_PARENTID("No such category by parentId", "parentId"),
    WRONG_NAME("The invalid name", "name"),
    NAME_CATEGORY_ALREADY_EXISTS("Name category already exists", "name"),
    UPDATE_SUBCATEGORY_ERROR_PARENTID("Subcategory can't become a main category", "parentId"),
    CATEGORY_LIST_ERROR("Category list does not match", "category"),
    WRONG_OLDPASSWORD("Wrong oldPassword", "oldPassword"),
    WRONG_NEWPASSWORD("Wrong newPassword", "newPassword"),
    PARENT_ID_ERROR("Wrong parentId ERROR", "parentId"),
    INVALID_LASTNAME("Incorrect lastname", "lastName"),
    INVALID_PATRONYMIC("Incorrect patronymic", "patronymic"),
    JSON_PARSE_EXCEPTION("Json parse exception", "json"),
    NULL_REQUEST("Null request", "json"),
    WRONG_REQUEST_PARAM("Wrong request param", "param"),
    WRONG_MEDIA_TYPE("Content type %s not supported for this URL", "contentType"),
    WRONG_URL("Wrong URL = %s", "url"),
    WRONG_OR_EMPTY_REQUEST_PARAM("Wrong or empty request param", "order"),
    WRONG_PRODUCT_PRICE("The product price is outdated", "price"),
    WRONG_PRODUCT_COUNT("The product count incorrect", "count"),
    NOT_ENOUGH_MONEY("Not enough money in the account", "amount"),
    PRODUCT_HAS_BEEN_DELETED("This product is not in storage", "product"),
    SUCH_PRODUCT_NO_IN_BASKET("Such product is not in basket", "basket"),
    INVALID_DEPOSIT("Enter positive deposit", "deposit"),
    DATABASE_ERROR("Database error", "database"),
    WRONG_VERSION_FOR_UPDATE("Version deposit incorrect", "version"),
    INVALID_PRODUCT_TO_BASKET("Impossible add product to basket", "product"),
    INCORRECT_PARAMS("Enter correct params", "param");

    String errorString;
    String field;

    AppErrorCode(String errorString, String field) {
        this.errorString = errorString;
        this.field = field;
    }

    public String getErrorString() {
        return errorString;
    }

    public String getField() {
        return field;
    }
}
