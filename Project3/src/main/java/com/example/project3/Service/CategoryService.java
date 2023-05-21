package com.example.project3.Service;

import com.example.project3.Model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getAll() {
        return categories;
    }

    public void add(Category category) {
        categories.add(category);
    }

    public boolean update(int id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (Integer.parseInt(categories.get(i).getId()) == id) {
                category.setId(categories.get(i).getId());
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        for (int i = 0; i < categories.size(); i++) {
            if (Integer.parseInt(categories.get(i).getId()) == id) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> checkErrors(Errors errors) {
        ArrayList<String> allErrors = new ArrayList<>();
        String field = null;
        if (errors.hasErrors()) {
            for (int i = 0; i < errors.getAllErrors().size(); i++) {
                field = errors.getFieldErrors().get(i).getField().concat(" : " + errors.getAllErrors().get(i).getDefaultMessage());
                allErrors.add(field);
            }
            return allErrors;
        }
        return null;
    }

    public String checkId(int id) {
        for (int i = 0; i < categories.size(); i++) {
            if (Integer.parseInt(categories.get(i).getId()) == id)
                return "This id is exist";
        }
        return null;
    }
}
