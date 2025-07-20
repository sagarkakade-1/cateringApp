import React from 'react';
import { Container, Card } from 'react-bootstrap';

const InventoryList = () => {
  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-box-seam me-2 text-primary"></i>
          Inventory Management
        </h1>
      </div>

      <Card>
        <Card.Body className="text-center py-5">
          <i className="bi bi-box-seam display-1 text-muted mb-3"></i>
          <h4>Inventory Management</h4>
          <p className="text-muted">
            Track utensils, display tables, water cans, food items, and equipment.<br />
            Monitor stock levels and get low stock alerts.<br />
            This module will be implemented in the next development phase.
          </p>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default InventoryList;

