export interface GetExpenseRequest {
  amountFrom?: number;
  amountTo?: number;
  description?: string;
  startDate?: string;
  endDate?: string;
  categoryId?: number;
}
