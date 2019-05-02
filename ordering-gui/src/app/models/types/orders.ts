import { CreateOrderActionType, Entity, Error, FindOrdersActionType, GetOrderActionType, Modified, OrderStatus, UpdateOrderActionType } from '../';
import { Item } from './';

export interface CreateOrder {
    customerId: string;
    name: string;
    description?: string;
}

export interface Order extends Entity {
    orderId: string;
    customerId: string;
    name: string;
    description?: string;
    status: OrderStatus;
    items: Item[];
}

export interface OrderState {
    loading: boolean;
    orders: Order[];
    error?: Error;
    modified?: Modified;
}

export interface GetOrderLoadingAction {
    type: GetOrderActionType.LOADING,
    loading: boolean
}

export interface GetOrderSuccessAction {
    type: GetOrderActionType.SUCCESS,
    payload: Order
}

export interface GetOrderErrorAction {
    type: GetOrderActionType.ERROR,
    error: any
}

export interface FindOrdersLoadingAction {
    type: FindOrdersActionType.LOADING,
    loading: boolean
}

export interface FindOrdersSuccessAction {
    type: FindOrdersActionType.SUCCESS,
    payload: Order[]
}

export interface FindOrdersErrorAction {
    type: FindOrdersActionType.ERROR,
    error: any
}

export interface CreateOrderLoadingAction {
    type: CreateOrderActionType.LOADING,
    loading: boolean
}

export interface CreateOrderSuccessAction {
    type: CreateOrderActionType.SUCCESS,
    headers: any
}

export interface CreateOrderErrorAction {
    type: CreateOrderActionType.ERROR,
    error: any
}

export interface UpdateOrderLoadingAction {
    type: UpdateOrderActionType.LOADING,
    loading: boolean
}

export interface UpdateOrderSuccessAction {
    type: UpdateOrderActionType.SUCCESS,
    payload: Order
}

export interface UpdateOrderErrorAction {
    type: UpdateOrderActionType.ERROR,
    error: any
}

export type GetOrderAction = GetOrderLoadingAction | GetOrderSuccessAction | GetOrderErrorAction;
export type FindOrdersAction = FindOrdersLoadingAction | FindOrdersSuccessAction | FindOrdersErrorAction;
export type CreateOrderAction = CreateOrderLoadingAction | CreateOrderSuccessAction | CreateOrderErrorAction;
export type UpdateOrderAction = UpdateOrderLoadingAction | UpdateOrderSuccessAction | UpdateOrderErrorAction;

export type OrderAction = GetOrderAction | FindOrdersAction | CreateOrderAction | UpdateOrderAction;
