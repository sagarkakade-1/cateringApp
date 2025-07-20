import React from 'react';
import { Container, Card } from 'react-bootstrap';

const OrderForm = () => {
  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-plus-circle me-2 text-primary"></i>
          Create New Order
        </h1>
      </div>

      <Card>
        <Card.Body className="text-center py-5">
          <i className="bi bi-plus-circle display-1 text-muted mb-3"></i>
          <h4>Order Form</h4>
          <p className="text-muted">
            Create and edit orders with full catering and half catering options.<br />
            This form will be implemented in the next development phase.
          </p>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default OrderForm;

