import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Table, Badge, Spinner } from 'react-bootstrap';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement } from 'chart.js';
import { Bar, Doughnut } from 'react-chartjs-2';

// Register ChartJS components
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement);

const Dashboard = () => {
  const [loading, setLoading] = useState(true);
  const [dashboardData, setDashboardData] = useState({
    stats: {
      totalOrders: 0,
      pendingOrders: 0,
      completedOrders: 0,
      totalRevenue: 0,
      totalEmployees: 0,
      activeEmployees: 0,
      totalCustomers: 0,
      pendingTasks: 0
    },
    recentOrders: [],
    upcomingEvents: [],
    lowStockItems: []
  });

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      setLoading(true);
      
      // Simulate API calls - replace with actual API calls
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      // Mock data for demonstration
      setDashboardData({
        stats: {
          totalOrders: 45,
          pendingOrders: 8,
          completedOrders: 32,
          totalRevenue: 450000,
          totalEmployees: 12,
          activeEmployees: 10,
          totalCustomers: 28,
          pendingTasks: 15
        },
        recentOrders: [
          { id: 1, orderNumber: 'ORD001', customer: 'Grand Hotel Palace', eventDate: '2024-01-15', status: 'COMPLETED', amount: 75000 },
          { id: 2, orderNumber: 'ORD002', customer: 'Sharma Wedding Planners', eventDate: '2024-01-20', status: 'IN_PROGRESS', amount: 150000 },
          { id: 3, orderNumber: 'ORD003', customer: 'Ravi Birthday Party', eventDate: '2024-01-25', status: 'PENDING', amount: 25000 },
          { id: 4, orderNumber: 'ORD004', customer: 'Corporate Events Ltd', eventDate: '2024-02-01', status: 'PENDING', amount: 100000 },
          { id: 5, orderNumber: 'ORD005', customer: 'Sunita Anniversary', eventDate: '2024-02-05', status: 'PENDING', amount: 40000 }
        ],
        upcomingEvents: [
          { id: 1, eventName: 'Ravi 30th Birthday Party', date: '2024-01-25', venue: 'Pune', guestCount: 50 },
          { id: 2, eventName: 'Product Launch Event', date: '2024-02-01', venue: 'Bangalore', guestCount: 200 },
          { id: 3, eventName: 'Sunita 25th Anniversary', date: '2024-02-05', venue: 'Hyderabad', guestCount: 80 }
        ],
        lowStockItems: [
          { id: 1, itemName: 'Water Glasses', currentStock: 75, minimumStock: 75, category: 'UTENSILS' },
          { id: 2, itemName: 'Green Vegetables Mix', currentStock: 8, minimumStock: 8, category: 'VEGETABLES' },
          { id: 3, itemName: 'Spices Mix', currentStock: 5, minimumStock: 5, category: 'GROCERY' }
        ]
      });
    } catch (error) {
      console.error('Error loading dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  const getStatusBadge = (status) => {
    const statusConfig = {
      'PENDING': { variant: 'warning', text: 'Pending' },
      'IN_PROGRESS': { variant: 'info', text: 'In Progress' },
      'COMPLETED': { variant: 'success', text: 'Completed' },
      'CANCELLED': { variant: 'danger', text: 'Cancelled' }
    };
    
    const config = statusConfig[status] || { variant: 'secondary', text: status };
    return <Badge bg={config.variant}>{config.text}</Badge>;
  };

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      minimumFractionDigits: 0
    }).format(amount);
  };

  // Chart data
  const orderStatusChartData = {
    labels: ['Pending', 'In Progress', 'Completed'],
    datasets: [
      {
        data: [dashboardData.stats.pendingOrders, 5, dashboardData.stats.completedOrders],
        backgroundColor: ['#ffc107', '#17a2b8', '#28a745'],
        borderWidth: 0
      }
    ]
  };

  const monthlyRevenueData = {
    labels: ['Oct', 'Nov', 'Dec', 'Jan'],
    datasets: [
      {
        label: 'Revenue (₹)',
        data: [320000, 380000, 420000, 450000],
        backgroundColor: 'rgba(102, 126, 234, 0.8)',
        borderColor: 'rgba(102, 126, 234, 1)',
        borderWidth: 2,
        borderRadius: 8
      }
    ]
  };

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '400px' }}>
        <div className="text-center">
          <Spinner animation="border" variant="primary" />
          <div className="mt-3">Loading dashboard...</div>
        </div>
      </div>
    );
  }

  return (
    <Container fluid className="fade-in">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="h3 mb-0 fw-bold">
          <i className="bi bi-speedometer2 me-2 text-primary"></i>
          Dashboard
        </h1>
        <div className="text-muted">
          <i className="bi bi-calendar3 me-1"></i>
          {new Date().toLocaleDateString('en-IN', { 
            weekday: 'long', 
            year: 'numeric', 
            month: 'long', 
            day: 'numeric' 
          })}
        </div>
      </div>

      {/* Stats Cards */}
      <Row className="mb-4">
        <Col lg={3} md={6} className="mb-3">
          <Card className="stats-card h-100" style={{ background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' }}>
            <Card.Body className="text-white text-center">
              <i className="bi bi-clipboard-check stats-icon"></i>
              <div className="stats-number">{dashboardData.stats.totalOrders}</div>
              <div className="stats-label">Total Orders</div>
            </Card.Body>
          </Card>
        </Col>
        <Col lg={3} md={6} className="mb-3">
          <Card className="stats-card h-100" style={{ background: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)' }}>
            <Card.Body className="text-white text-center">
              <i className="bi bi-clock-history stats-icon"></i>
              <div className="stats-number">{dashboardData.stats.pendingOrders}</div>
              <div className="stats-label">Pending Orders</div>
            </Card.Body>
          </Card>
        </Col>
        <Col lg={3} md={6} className="mb-3">
          <Card className="stats-card h-100" style={{ background: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)' }}>
            <Card.Body className="text-white text-center">
              <i className="bi bi-currency-rupee stats-icon"></i>
              <div className="stats-number">{formatCurrency(dashboardData.stats.totalRevenue).replace('₹', '')}</div>
              <div className="stats-label">Total Revenue</div>
            </Card.Body>
          </Card>
        </Col>
        <Col lg={3} md={6} className="mb-3">
          <Card className="stats-card h-100" style={{ background: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)' }}>
            <Card.Body className="text-white text-center">
              <i className="bi bi-people stats-icon"></i>
              <div className="stats-number">{dashboardData.stats.activeEmployees}</div>
              <div className="stats-label">Active Employees</div>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        {/* Recent Orders */}
        <Col lg={8} className="mb-4">
          <Card className="h-100">
            <Card.Header className="d-flex justify-content-between align-items-center">
              <h5 className="mb-0">
                <i className="bi bi-list-ul me-2"></i>
                Recent Orders
              </h5>
              <small className="text-muted">Last 5 orders</small>
            </Card.Header>
            <Card.Body className="p-0">
              <Table responsive hover className="mb-0">
                <thead>
                  <tr>
                    <th>Order #</th>
                    <th>Customer</th>
                    <th>Event Date</th>
                    <th>Status</th>
                    <th>Amount</th>
                  </tr>
                </thead>
                <tbody>
                  {dashboardData.recentOrders.map(order => (
                    <tr key={order.id}>
                      <td className="fw-semibold">{order.orderNumber}</td>
                      <td>{order.customer}</td>
                      <td>{new Date(order.eventDate).toLocaleDateString('en-IN')}</td>
                      <td>{getStatusBadge(order.status)}</td>
                      <td className="fw-semibold">{formatCurrency(order.amount)}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>

        {/* Order Status Chart */}
        <Col lg={4} className="mb-4">
          <Card className="h-100">
            <Card.Header>
              <h5 className="mb-0">
                <i className="bi bi-pie-chart me-2"></i>
                Order Status
              </h5>
            </Card.Header>
            <Card.Body className="d-flex align-items-center justify-content-center">
              <div style={{ width: '250px', height: '250px' }}>
                <Doughnut 
                  data={orderStatusChartData}
                  options={{
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                      legend: {
                        position: 'bottom'
                      }
                    }
                  }}
                />
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        {/* Monthly Revenue Chart */}
        <Col lg={8} className="mb-4">
          <Card>
            <Card.Header>
              <h5 className="mb-0">
                <i className="bi bi-graph-up me-2"></i>
                Monthly Revenue Trend
              </h5>
            </Card.Header>
            <Card.Body>
              <Bar 
                data={monthlyRevenueData}
                options={{
                  responsive: true,
                  plugins: {
                    legend: {
                      display: false
                    }
                  },
                  scales: {
                    y: {
                      beginAtZero: true,
                      ticks: {
                        callback: function(value) {
                          return '₹' + (value / 1000) + 'K';
                        }
                      }
                    }
                  }
                }}
              />
            </Card.Body>
          </Card>
        </Col>

        {/* Upcoming Events & Low Stock */}
        <Col lg={4}>
          <Row>
            <Col className="mb-4">
              <Card>
                <Card.Header>
                  <h6 className="mb-0">
                    <i className="bi bi-calendar-event me-2"></i>
                    Upcoming Events
                  </h6>
                </Card.Header>
                <Card.Body className="p-0">
                  {dashboardData.upcomingEvents.map(event => (
                    <div key={event.id} className="p-3 border-bottom">
                      <div className="fw-semibold">{event.eventName}</div>
                      <small className="text-muted">
                        <i className="bi bi-geo-alt me-1"></i>
                        {event.venue} • {event.guestCount} guests
                      </small>
                      <div className="text-primary small">
                        <i className="bi bi-calendar3 me-1"></i>
                        {new Date(event.date).toLocaleDateString('en-IN')}
                      </div>
                    </div>
                  ))}
                </Card.Body>
              </Card>
            </Col>
          </Row>
          
          <Row>
            <Col>
              <Card>
                <Card.Header>
                  <h6 className="mb-0">
                    <i className="bi bi-exclamation-triangle me-2 text-warning"></i>
                    Low Stock Alert
                  </h6>
                </Card.Header>
                <Card.Body className="p-0">
                  {dashboardData.lowStockItems.map(item => (
                    <div key={item.id} className="p-3 border-bottom">
                      <div className="fw-semibold">{item.itemName}</div>
                      <small className="text-muted">{item.category}</small>
                      <div className="text-warning small">
                        <i className="bi bi-box me-1"></i>
                        Stock: {item.currentStock} (Min: {item.minimumStock})
                      </div>
                    </div>
                  ))}
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;

