// Home.jsx
import React from 'react';
import { Container, Typography } from '@mui/material';

function Home() {
  return (
    <Container>
      <Typography variant="h4" component="h1" gutterBottom>Home</Typography>
      <Typography variant="body1">Welcome to the Survey Service!</Typography>
    </Container>
  );
}

export default Home;
