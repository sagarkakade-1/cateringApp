import React from 'react';
import { Container, Card } from 'react-bootstrap';

const CustomerList = () => {
  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-person-hearts me-2 text-primary"></i>
          Customer Management
        </h1>
      </div>

      <Card>
        <Card.Body className="text-center py-5">
          <i className="bi bi-person-hearts display-1 text-muted mb-3"></i>
          <h4>Customer Management</h4>
          <p className="text-muted">
            Manage one-time customers and permanent clients.<br />
            Track payments: Advance, Remaining, Fully Paid.<br />
            This module will be implemented in the next development phase.
          </p>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default CustomerList;

