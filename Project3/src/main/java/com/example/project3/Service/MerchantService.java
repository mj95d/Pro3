package com.example.project3.Service;

import org.springframework.stereotype.Service;

@Service
public class MerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();
    public ArrayList<Merchant> getAll(){
        return merchants;
    }
    public void add(Merchant merchant){
        merchants.add(merchant);
    }
    public boolean update(int id, Merchant merchant){
        for(int i = 0; i < merchants.size(); i++){
            if(Integer.parseInt(merchants.get(i).getId()) == id){
                merchant.setId(merchants.get(i).getId());
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;

    }
    public boolean delete(int id){
        for(int i = 0; i < merchants.size(); i++){
            if(Integer.parseInt(merchants.get(i).getId()) == id){
                merchants.remove(i);
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
        for (int i = 0; i < merchants.size(); i++) {
            if (Integer.parseInt(merchants.get(i).getId()) == id)
                return "This id is exist";
        }
        return null;
    }
}