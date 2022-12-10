import {FC, ReactElement, useEffect, useState} from "react";
import {useRouter} from "next/router";
import {useForm} from "react-hook-form";
import {FormattedMessage, useIntl} from "react-intl";
import {Button, Form, Icon, Message, Segment} from "semantic-ui-react";
import {v4 as uuid} from "uuid";
import {ErrorPanelFragment, LoadingIndicatorFragment, mapErrorPayload} from "../fragments";
import {ClientError, CreateOrder, ErrorPayload, Order, PageState} from "../types";
import {RestClient} from "../core/client";

const CreateOrderPage: FC = (): ReactElement => {

    const router = useRouter();
    const {formatMessage: t} = useIntl();
    const [pageState, setPageState] = useState<PageState<Order>>({status: 'PENDING'});
    const {register, handleSubmit, formState: {errors: formErrors}} = useForm();

    useEffect(() => {
        if (pageState.status === 'SUCCESS' && !!pageState.data) {
            const {orderId} = pageState.data;
            router.push(`/orders/${orderId}`);
        }
    }, [pageState]);

    const createOrder = (body: CreateOrder) => {
        setPageState({status: 'LOADING'})
        RestClient.POST<Order>("/api/orders", body)
            .then(response => {
                setPageState({status: 'SUCCESS', data: response.body});
            })
            .catch(e => {
                const error = e as ClientError<ErrorPayload>;
                setPageState({status: 'FAILED', error: error.response?.body});
            });
    };

    const onFormSubmit = (formData: any) => {
        createOrder({
            customerId: uuid(),
            name: formData.orderName,
            description: formData.orderDescription
        });
    };
    const onFormError = (formErrors: any) => {
        console.log("FORM ERROR", formErrors)
    };

    const onCancelButtonClick = () => {
        router.push('/');
    };

    if (pageState.status === 'LOADING') {
        return <LoadingIndicatorFragment/>;
    } else if (pageState.status === 'FAILED') {
        if (!!pageState.error) {
            const {errorId, errorCode} = mapErrorPayload(pageState.error);
            return (
                <ErrorPanelFragment errorId={errorId} errorCode={errorCode}/>
            );
        } else {
            return (
                <ErrorPanelFragment errorCode={'ACNTECH.FUNCTIONAL.COMMON.MISSING_ERROR_RESPONSE'}/>
            );
        }
    } else if (pageState.status === 'SUCCESS') {
        if (!!pageState.data) {
            return <></>; // Will be redirected
        } else {
            return (
                <ErrorPanelFragment errorCode={'ACNTECH.FUNCTIONAL.ORDERS.ORDER_NOT_FOUND'}/>
            );
        }
    } else {
        return (
            <Segment basic>
                <Form onSubmit={handleSubmit(onFormSubmit, onFormError)}
                      error={!!Object.keys(formErrors).length}>
                    <Form.Group>
                        <Form.Field error={!!formErrors.orderName}>
                            <label>{t({id: 'form.create-order.field.order-name.label'})}</label>
                            <input type="text" size={20}
                                   placeholder={t({id: 'form.create-order.field.order-name.placeholder'})}
                                   {...register("orderName", {required: true, minLength: 4, maxLength: 20})} />
                        </Form.Field>
                    </Form.Group>
                    <Form.Group>
                        <Form.Field error={!!formErrors.orderDescription}>
                            <label>{t({id: 'form.create-order.field.order-description.label'})}</label>
                            <input type="text" size={60}
                                   placeholder={t({id: 'form.create-order.field.order-description.placeholder'})}
                                   {...register("orderDescription", {maxLength: 200})} />
                        </Form.Field>
                    </Form.Group>
                    <Form.Group>
                        <Form.Button primary size="tiny">
                            <Icon name="dolly"/><FormattedMessage id="button.submit"/>
                        </Form.Button>
                        <Button secondary size="tiny" onClick={onCancelButtonClick}>
                            <Icon name="cancel"/><FormattedMessage id="button.cancel"/>
                        </Button>
                    </Form.Group>
                    <Message error><Icon name="ban"/> {t({id: 'form.create-order.error'})}</Message>
                </Form>
            </Segment>
        );
    }
};

export default CreateOrderPage;
