import React from 'react';
import { Container, Card } from 'react-bootstrap';

const Reports = () => {
  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-graph-up me-2 text-primary"></i>
          Reports & Analytics
        </h1>
      </div>

      <Card>
        <Card.Body className="text-center py-5">
          <i className="bi bi-graph-up display-1 text-muted mb-3"></i>
          <h4>Reports & Analytics</h4>
          <p className="text-muted">
            Employee-wise reports: orders completed, payment due.<br />
            Order-wise reports: summary of resources, total cost, client.<br />
            Monthly reports with export to PDF/CSV.<br />
            This module will be implemented in the next development phase.
          </p>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default Reports;

