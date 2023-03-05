package pro.sky.budgetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Transaction {
    private  Category category;
    private int sum;
    private String comment;
}
