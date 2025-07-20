import React from 'react';
import { Container, Card } from 'react-bootstrap';

const TaskList = () => {
  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-check2-square me-2 text-primary"></i>
          Task Management
        </h1>
      </div>

      <Card>
        <Card.Body className="text-center py-5">
          <i className="bi bi-check2-square display-1 text-muted mb-3"></i>
          <h4>Task Management</h4>
          <p className="text-muted">
            Add tasks per order with status: To-do, In Progress, Done, Deleted.<br />
            View tasks per employee or per order.<br />
            This module will be implemented in the next development phase.
          </p>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default TaskList;

