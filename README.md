# Order Service

## Signup

```graphql
mutation($request: SignupRequest!) {
  signup(request: $request) {
    username
    email
    roles
  }
}
```

and query variable

```graphql
{
  "request": {
    "username": "ashutosh",
    "password": "ashu@123",
    "email": "ashu@email.com"
  }
}
```


## Signin

```graphql
mutation($request: LoginRequest!){
  login(request: $request){
    token
    username
    roles
  }
}
```

and query variable

```graphql
{
  "request": {
    "username": "ashutosh",
    "password": "ashu@123"
  }
}
```
Use the response token to add into Authorization header for all the following requests.

## Create product

```graphql
mutation($product: ProductInput!) {
  createProduct(product: $product) {
    id
    name
  }
}
```

and query variable

```graphql
{
  "product": {
    "id": "1",
    "name": "samsung tv",
    "description": "Its a good tv",
    "price": "20000"
  }
}
```

## Query product by id

```graphql
query {
  productById(id: "1") {
    id
    name
  }
}
```

## Query customer by id

```graphql
query {
  customerById(id: "1") {
    id
    name
    orders {
      quantity
      id
      product {
        id
        name
      }
    }
  }
}
```

## Create order

```graphql
mutation($order: CreateOrderInput!) {
  createOrder(order: $order) {
    id
    customer {
      name
    }
    product {
      name
      description
      price
    }
    quantity
    status
  }
}
```

and query variable

```graphql
{
  "order": {
   "customerId": "1",
    "productId": "1",
    "quantity": 3
  }
}
```
