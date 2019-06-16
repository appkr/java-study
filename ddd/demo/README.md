## DDD Demo

- [애그리게잇 하나에 리파지토리 하나](https://www.popit.kr/에그리게잇-하나에-리파지토리-하나/)
- [ID로 다른 애그리게잇을 참조하라](https://www.popit.kr/id로-다른-애그리게잇을-참조하라/)

```bash
# OrderTest.canPlaceAnOrder()
order = Order{id=null, createdAt=null, lineItems=[LineItem{id=null, productId='P-0001', name='상품 A', price=1000, quantity=2}, LineItem{id=null, productId='P-0002', name='상품 B', price=2000, quantity=1}], payments=[CreditCardPayment{cardNumber='1234-123', id=null, method=CREDIT_CARD, amount=2000}, MobilePhonePayment{phoneNumber='010-0000-0000', id=null, method=MOBILE_PHONE, amount=2000}], shippingAddress=ShippingAddress{id=null, zipCode='12345', receipient='Some~~Body'}}
```