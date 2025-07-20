/**
 * Dashboard specific JavaScript
 * Handles charts and dashboard functionality
 */

document.addEventListener('DOMContentLoaded', function() {
    // Initialize charts
    initializeOrderStatusChart();
    initializeRevenueChart();
});

/**
 * Initialize Order Status Doughnut Chart
 */
function initializeOrderStatusChart() {
    const ctx = document.getElementById('orderStatusChart');
    if (!ctx) return;

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Completed', 'In Progress', 'Pending', 'Cancelled'],
            datasets: [{
                data: [25, 8, 10, 2],
                backgroundColor: [
                    '#28a745',  // Green for Completed
                    '#17a2b8',  // Blue for In Progress
                    '#ffc107',  // Yellow for Pending
                    '#dc3545'   // Red for Cancelled
                ],
                borderWidth: 0,
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        padding: 20,
                        usePointStyle: true,
                        font: {
                            size: 12
                        }
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            const label = context.label || '';
                            const value = context.parsed;
                            const total = context.dataset.data.reduce((a, b) => a + b, 0);
                            const percentage = Math.round((value / total) * 100);
                            return `${label}: ${value} (${percentage}%)`;
                        }
                    }
                }
            },
            cutout: '60%',
            animation: {
                animateRotate: true,
                duration: 1000
            }
        }
    });
}

/**
 * Initialize Monthly Revenue Bar Chart
 */
function initializeRevenueChart() {
    const ctx = document.getElementById('revenueChart');
    if (!ctx) return;

    const gradient = ctx.getContext('2d').createLinearGradient(0, 0, 0, 400);
    gradient.addColorStop(0, 'rgba(102, 126, 234, 0.8)');
    gradient.addColorStop(1, 'rgba(118, 75, 162, 0.2)');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan'],
            datasets: [{
                label: 'Revenue (₹)',
                data: [320000, 280000, 450000, 380000, 520000, 450000],
                backgroundColor: gradient,
                borderColor: '#667eea',
                borderWidth: 2,
                borderRadius: 8,
                borderSkipped: false,
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return `Revenue: ${formatCurrency(context.parsed.y)}`;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return '₹' + (value / 1000) + 'K';
                        }
                    },
                    grid: {
                        color: 'rgba(0, 0, 0, 0.1)'
                    }
                },
                x: {
                    grid: {
                        display: false
                    }
                }
            },
            animation: {
                duration: 1000,
                easing: 'easeInOutQuart'
            }
        }
    });
}

/**
 * Update dashboard statistics (can be called via AJAX)
 */
function updateDashboardStats(stats) {
    if (stats.totalOrders) {
        const totalOrdersElement = document.querySelector('.stats-card:nth-child(1) .stats-number');
        if (totalOrdersElement) {
            animateNumber(totalOrdersElement, stats.totalOrders);
        }
    }
    
    if (stats.pendingOrders) {
        const pendingOrdersElement = document.querySelector('.stats-card:nth-child(2) .stats-number');
        if (pendingOrdersElement) {
            animateNumber(pendingOrdersElement, stats.pendingOrders);
        }
    }
    
    if (stats.totalRevenue) {
        const totalRevenueElement = document.querySelector('.stats-card:nth-child(3) .stats-number');
        if (totalRevenueElement) {
            animateNumber(totalRevenueElement, stats.totalRevenue, true);
        }
    }
    
    if (stats.activeEmployees) {
        const activeEmployeesElement = document.querySelector('.stats-card:nth-child(4) .stats-number');
        if (activeEmployeesElement) {
            animateNumber(activeEmployeesElement, stats.activeEmployees);
        }
    }
}

/**
 * Animate number counting effect
 */
function animateNumber(element, targetValue, isCurrency = false) {
    const startValue = 0;
    const duration = 1000;
    const startTime = performance.now();
    
    function updateNumber(currentTime) {
        const elapsed = currentTime - startTime;
        const progress = Math.min(elapsed / duration, 1);
        
        // Easing function
        const easeOutQuart = 1 - Math.pow(1 - progress, 4);
        const currentValue = Math.floor(startValue + (targetValue - startValue) * easeOutQuart);
        
        if (isCurrency) {
            element.textContent = formatCurrency(currentValue);
        } else {
            element.textContent = currentValue.toLocaleString('en-IN');
        }
        
        if (progress < 1) {
            requestAnimationFrame(updateNumber);
        }
    }
    
    requestAnimationFrame(updateNumber);
}

/**
 * Refresh dashboard data (can be called periodically)
 */
function refreshDashboard() {
    // This function can be implemented to fetch fresh data from the server
    // For now, it's a placeholder for future AJAX implementation
    
    console.log('Refreshing dashboard data...');
    
    // Example AJAX call (uncomment when backend endpoints are ready):
    /*
    fetch('/api/dashboard/stats')
        .then(response => response.json())
        .then(data => {
            updateDashboardStats(data);
        })
        .catch(error => {
            console.error('Error refreshing dashboard:', error);
        });
    */
}

/**
 * Initialize auto-refresh (optional)
 */
function initializeAutoRefresh() {
    // Refresh dashboard every 5 minutes
    setInterval(refreshDashboard, 5 * 60 * 1000);
}

// Uncomment to enable auto-refresh
// initializeAutoRefresh();

