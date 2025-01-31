package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class PaymentHistoryStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "study/payment/payment-history";
    }
}
