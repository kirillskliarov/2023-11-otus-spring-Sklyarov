import { GetExpenseCategoryResponse } from './get-expense-category-response';

export interface GetExpenseResponse {
  id: number;
  amount: number;
  description: string;
  date: string;
  expenseCategory: GetExpenseCategoryResponse;
}
