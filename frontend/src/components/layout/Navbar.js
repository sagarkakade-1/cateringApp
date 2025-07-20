import React from 'react';
import { Navbar as BootstrapNavbar, Nav, Dropdown, Container } from 'react-bootstrap';
import { authService } from '../../services/authService';

const Navbar = ({ onLogout, onToggleSidebar }) => {
  const currentUser = authService.getCurrentUser();

  const handleLogout = async () => {
    try {
      await authService.logout();
      onLogout();
    } catch (error) {
      console.error('Logout error:', error);
      // Force logout even if request fails
      onLogout();
    }
  };

  return (
    <BootstrapNavbar 
      bg="white" 
      expand="lg" 
      className="shadow-sm border-bottom"
      style={{ height: '60px' }}
    >
      <Container fluid>
        <div className="d-flex align-items-center">
          <button
            className="btn btn-link text-dark p-0 me-3"
            onClick={onToggleSidebar}
            style={{ fontSize: '1.2rem' }}
          >
            <i className="bi bi-list"></i>
          </button>
          
          <BootstrapNavbar.Brand className="fw-bold text-primary mb-0">
            <i className="bi bi-cup-hot me-2"></i>
            Catering Management
          </BootstrapNavbar.Brand>
        </div>

        <Nav className="ms-auto">
          <Dropdown align="end">
            <Dropdown.Toggle 
              variant="link" 
              className="text-decoration-none text-dark d-flex align-items-center"
              id="user-dropdown"
            >
              <div className="d-flex align-items-center">
                <div 
                  className="rounded-circle bg-primary text-white d-flex align-items-center justify-content-center me-2"
                  style={{ width: '35px', height: '35px', fontSize: '0.9rem' }}
                >
                  {currentUser?.fullName ? currentUser.fullName.charAt(0).toUpperCase() : 'A'}
                </div>
                <div className="d-none d-md-block text-start">
                  <div className="fw-semibold" style={{ fontSize: '0.9rem' }}>
                    {currentUser?.fullName || 'Admin User'}
                  </div>
                  <div className="text-muted" style={{ fontSize: '0.75rem' }}>
                    {currentUser?.role || 'Administrator'}
                  </div>
                </div>
                <i className="bi bi-chevron-down ms-2"></i>
              </div>
            </Dropdown.Toggle>

            <Dropdown.Menu className="shadow border-0" style={{ borderRadius: '10px' }}>
              <Dropdown.Header>
                <div className="fw-semibold">{currentUser?.fullName || 'Admin User'}</div>
                <div className="text-muted small">{currentUser?.email || 'admin@catering.com'}</div>
              </Dropdown.Header>
              
              <Dropdown.Divider />
              
              <Dropdown.Item href="#" className="d-flex align-items-center">
                <i className="bi bi-person me-2"></i>
                Profile Settings
              </Dropdown.Item>
              
              <Dropdown.Item href="#" className="d-flex align-items-center">
                <i className="bi bi-gear me-2"></i>
                System Settings
              </Dropdown.Item>
              
              <Dropdown.Divider />
              
              <Dropdown.Item 
                onClick={handleLogout}
                className="d-flex align-items-center text-danger"
              >
                <i className="bi bi-box-arrow-right me-2"></i>
                Sign Out
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Nav>
      </Container>
    </BootstrapNavbar>
  );
};

export default Navbar;

