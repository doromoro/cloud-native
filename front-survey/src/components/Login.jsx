import React, { useState } from 'react';
import axios from 'axios';
import { TextField, Button, Container, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

function Login({ setIsLoggedIn }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
        const response = await axios.post('/api/survey/auth/login', {
            email,
            password
        });
        
        if (response.data.status === 'success') {
            localStorage.clear()
            localStorage.setItem('id', response.data.memberId)
            localStorage.setItem('role', response.data.role)
            setIsLoggedIn(true);
            navigate('/');
        } else {
            console.error('Login failed:', response.data.message);
        }
        } catch (error) {
        console.error('Login error:', error);
        }
    };

    return (
        <Container>
        <Typography variant="h4" component="h1" gutterBottom>Login</Typography>
        <form onSubmit={handleLogin}>
            <TextField
            label="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            fullWidth
            margin="normal"
            required
            />
            <TextField
            label="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            fullWidth
            margin="normal"
            required
            />
            <Button type="submit" variant="contained" color="primary" fullWidth>
            Login
            </Button>
        </form>
        </Container>
    );
}

export default Login;
