import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import Login from './components/Login';
import Home from './components/Home';


function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogout = async () => {
    try {
        const id = localStorage.getItem("id");
        
        const response = await axios.get(`/api/survey/auth/logout?id=${id}`);
        if (response.data.status === 'success') {
            localStorage.clear();
            setIsLoggedIn(false);
            navigate('/');
        } else {
            console.error('Logout failed:', response.data.message);
        }
    } catch (error) {
    console.error('Logout error:', error);
    }
};

  return (
    <Router>
      <NavBar isLoggedIn={isLoggedIn} handleLogout={handleLogout} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />
        {/* <Route path="/survey" element={<SurveyIntro />} /> */}
      </Routes>
    </Router>
  );
}

export default App;
