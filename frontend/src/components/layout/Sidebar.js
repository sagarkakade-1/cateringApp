import React from 'react';
import { Nav } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';

const Sidebar = ({ collapsed }) => {
  const location = useLocation();
  const navigate = useNavigate();

  const menuItems = [
    {
      path: '/dashboard',
      icon: 'bi-speedometer2',
      label: 'Dashboard',
      color: '#667eea'
    },
    {
      path: '/orders',
      icon: 'bi-clipboard-check',
      label: 'Orders',
      color: '#f093fb'
    },
    {
      path: '/employees',
      icon: 'bi-people',
      label: 'Employees',
      color: '#4facfe'
    },
    {
      path: '/inventory',
      icon: 'bi-box-seam',
      label: 'Inventory',
      color: '#43e97b'
    },
    {
      path: '/customers',
      icon: 'bi-person-hearts',
      label: 'Customers',
      color: '#fa709a'
    },
    {
      path: '/tasks',
      icon: 'bi-check2-square',
      label: 'Tasks',
      color: '#ffecd2'
    },
    {
      path: '/reports',
      icon: 'bi-graph-up',
      label: 'Reports',
      color: '#a8edea'
    }
  ];

  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <div 
      className={`sidebar bg-white shadow-sm border-end ${collapsed ? 'collapsed' : ''}`}
      style={{
        position: 'fixed',
        top: '60px',
        left: 0,
        height: 'calc(100vh - 60px)',
        width: collapsed ? '80px' : '250px',
        transition: 'width 0.3s ease',
        zIndex: 1000,
        overflowY: 'auto'
      }}
    >
      <Nav className="flex-column p-3">
        {menuItems.map((item) => {
          const isActive = location.pathname === item.path;
          
          return (
            <Nav.Item key={item.path} className="mb-2">
              <Nav.Link
                onClick={() => handleNavigation(item.path)}
                className={`d-flex align-items-center text-decoration-none rounded-3 p-3 ${
                  isActive ? 'active' : ''
                }`}
                style={{
                  cursor: 'pointer',
                  backgroundColor: isActive ? `${item.color}15` : 'transparent',
                  color: isActive ? item.color : '#6c757d',
                  border: isActive ? `2px solid ${item.color}30` : '2px solid transparent',
                  transition: 'all 0.3s ease',
                  fontWeight: isActive ? '600' : '500'
                }}
                onMouseEnter={(e) => {
                  if (!isActive) {
                    e.target.style.backgroundColor = '#f8f9fa';
                    e.target.style.color = item.color;
                  }
                }}
                onMouseLeave={(e) => {
                  if (!isActive) {
                    e.target.style.backgroundColor = 'transparent';
                    e.target.style.color = '#6c757d';
                  }
                }}
              >
                <i 
                  className={`bi ${item.icon} ${collapsed ? 'fs-5' : 'me-3'}`}
                  style={{ 
                    color: isActive ? item.color : 'inherit',
                    minWidth: '20px'
                  }}
                ></i>
                {!collapsed && (
                  <span className="sidebar-text">{item.label}</span>
                )}
              </Nav.Link>
            </Nav.Item>
          );
        })}
      </Nav>

      {/* Sidebar Footer */}
      {!collapsed && (
        <div className="sidebar-footer p-3 mt-auto">
          <div className="bg-light rounded-3 p-3 text-center">
            <i className="bi bi-info-circle text-primary mb-2 d-block fs-4"></i>
            <small className="text-muted">
              <strong>Catering Management v1.0</strong><br />
              Complete business solution
            </small>
          </div>
        </div>
      )}
    </div>
  );
};

export default Sidebar;

