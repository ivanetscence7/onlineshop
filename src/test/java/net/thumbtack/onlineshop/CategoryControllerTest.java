package net.thumbtack.onlineshop;


import net.thumbtack.onlineshop.dto.response.AllCategoryDtoResponse;
import net.thumbtack.onlineshop.dto.response.CategoryDtoResponse;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static org.junit.Assert.assertEquals;

public class CategoryControllerTest extends UserTestBase {

    @Test
    public void testAddCategory() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ResponseEntity mainCategory = addCategory(token, "audi", null, SUCCESS);
        CategoryDtoResponse categoryBody = (CategoryDtoResponse) mainCategory.getBody();

        ResponseEntity subCategory = addCategory(token, "audi rs5", categoryBody.getId(), SUCCESS);
        CategoryDtoResponse subCategoryBody = (CategoryDtoResponse) subCategory.getBody();

        addCategory(token, "mazda", 0, SUCCESS);
        addCategory(token, "volvo", null, SUCCESS);
        addCategory(token, "volvo", null, NAME_CATEGORY_ALREADY_EXISTS);
        addCategory(token, "", null, WRONG_NAME);

        addCategory(token, "audi a4", subCategoryBody.getId(), SUBCATEGORY_ERROR);
        addCategory(token, "audi a5", 12345, NO_SUCH_CATEGORY_BY_PARENTID);
    }

    @Test
    public void testGetCategoryById() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        CategoryDtoResponse mainCategoryOne = (CategoryDtoResponse) addCategory(token, "bmw", null, SUCCESS).getBody();
        CategoryDtoResponse mainCategoryTwo = (CategoryDtoResponse) addCategory(token, "audi", null, SUCCESS).getBody();

        CategoryDtoResponse subCategoryOne = (CategoryDtoResponse) addCategory(token, "bmw x6", mainCategoryOne.getId(), SUCCESS).getBody();
        CategoryDtoResponse subCategoryTwo = (CategoryDtoResponse) addCategory(token, "audi a4", mainCategoryTwo.getId(), SUCCESS).getBody();

        ResponseEntity categoryResponseOne = getCategoryById(token, mainCategoryOne.getId(), SUCCESS);
        ResponseEntity categoryResponseTwo = getCategoryById(token, subCategoryOne.getId(), SUCCESS);

        CategoryDtoResponse categoryOne = (CategoryDtoResponse) categoryResponseOne.getBody();
        assertEquals("bmw", categoryOne.getName());
        CategoryDtoResponse categoryTwo = (CategoryDtoResponse) categoryResponseTwo.getBody();
        assertEquals("bmw x6", categoryTwo.getName());

    }

    @Test
    public void testUpdateCategory() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        CategoryDtoResponse mainCategoryOne = (CategoryDtoResponse) addCategory(token, "bmw", null, SUCCESS).getBody();
        CategoryDtoResponse mainCategoryTwo = (CategoryDtoResponse) addCategory(token, "audi", null, SUCCESS).getBody();

        CategoryDtoResponse subCategoryOne = (CategoryDtoResponse) addCategory(token, "bmw x6", mainCategoryOne.getId(), SUCCESS).getBody();
        CategoryDtoResponse subCategoryTwo = (CategoryDtoResponse) addCategory(token, "audi a4", mainCategoryTwo.getId(), SUCCESS).getBody();

        //TODO переименование главной категории
        updateCategory(token, mainCategoryOne.getId(), "BMW", null, SUCCESS);

        //TODO переименование подкатегории
        updateCategory(token, subCategoryOne.getId(), "bmw x6", mainCategoryTwo.getId(), SUCCESS);

        //TODO попытаться категорию сделать подкатегорией
        updateCategory(token, mainCategoryOne.getId(), null, mainCategoryOne.getId() + 1, UPDATE_CATEGORY_ERROR_PARENTID);

        //TODO попытаться подкатегорию сделать категорией
        updateCategory(token, subCategoryTwo.getId(), "audi rs5", 0, PARENT_ID_ERROR);

        //TODO попытаться передать неизвестный categoryId
        updateCategory(token, 11111, "bmw", mainCategoryOne.getId(), NO_SUCH_CATEGORY_BY_ID);
    }

    @Test
    public void testDeleteCategory() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);

        ResponseEntity mainCategory = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), "ferrari", null, SUCCESS);
        CategoryDtoResponse categoryBody = (CategoryDtoResponse) mainCategory.getBody();

        ResponseEntity subCategory = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), "ferrari 360 spider", categoryBody.getId(), SUCCESS);
        CategoryDtoResponse subCategoryBody = (CategoryDtoResponse) subCategory.getBody();

        deleteCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), categoryBody.getId(), SUCCESS);
        deleteCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), subCategoryBody.getId(), SUCCESS);

    }

    @Test
    public void testGetAllCategories() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        //--
        ResponseEntity categoryOne = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "bmw", null, SUCCESS);
        CategoryDtoResponse categoryDtoResponseOne = (CategoryDtoResponse) categoryOne.getBody();

        addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "bmw x5", categoryDtoResponseOne.getId(), SUCCESS);

        addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "bmw x6", categoryDtoResponseOne.getId(), SUCCESS);
        //--

        //--
        ResponseEntity categoryTwo = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "audi", null, SUCCESS);
        CategoryDtoResponse categoryDtoResponseTwo = (CategoryDtoResponse) categoryTwo.getBody();

        addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "audi r8", categoryDtoResponseTwo.getId(), SUCCESS);

        addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "audi a4", categoryDtoResponseTwo.getId(), SUCCESS);
        //--

        //--
        ResponseEntity categoryThree = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "lada", null, SUCCESS);
        CategoryDtoResponse categoryDtoResponseThree = (CategoryDtoResponse) categoryThree.getBody();

        addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "lada vesta", categoryDtoResponseThree.getId(), SUCCESS);

        addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "lada xray", categoryDtoResponseThree.getId(), SUCCESS);
        //--

        ResponseEntity allCategoriesResponse =
                getCategories(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), SUCCESS);
        AllCategoryDtoResponse allCategories = (AllCategoryDtoResponse) allCategoriesResponse.getBody();


    }

}
