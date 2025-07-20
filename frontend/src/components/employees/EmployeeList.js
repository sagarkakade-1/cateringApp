import React from 'react';
import { Container, Card } from 'react-bootstrap';

const EmployeeList = () => {
  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-people me-2 text-primary"></i>
          Employee Management
        </h1>
      </div>

      <Card>
        <Card.Body className="text-center py-5">
          <i className="bi bi-people display-1 text-muted mb-3"></i>
          <h4>Employee Management</h4>
          <p className="text-muted">
            Manage employees: Cook, Bai, Waiter, Driver, Display Table Boy, Service Boy.<br />
            Track orders served and calculate payments.<br />
            This module will be implemented in the next development phase.
          </p>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default EmployeeList;

