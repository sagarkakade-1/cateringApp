import React from 'react';
import { Container, Card } from 'react-bootstrap';

const EmployeeForm = () => {
  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-person-plus me-2 text-primary"></i>
          Add/Edit Employee
        </h1>
      </div>

      <Card>
        <Card.Body className="text-center py-5">
          <i className="bi bi-person-plus display-1 text-muted mb-3"></i>
          <h4>Employee Form</h4>
          <p className="text-muted">
            Add new employees or edit existing employee information.<br />
            This form will be implemented in the next development phase.
          </p>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default EmployeeForm;

