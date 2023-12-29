import { useEffect, useState } from 'react'
import { Outlet, RouterProvider, createBrowserRouter } from 'react-router-dom';

import styled, { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import ThemeProvider from './components/themeProvider';
import { dark, light } from './components/theme';
import ProtectedRoute from './components/protected-route';
import Home from './routes/home';
import Map from './routes/map';
import Test from './routes/test';
import Profile from './routes/profile';
import Login from './routes/login';
import ThemeToggle from './components/darkmode-btn';



const router = createBrowserRouter([
  {
    path: '/',
    element: <Outlet />,
    children: [
      {
        path: '',
        element: <Home />,
      },
      {
        path: 'map',
        element: <Map />,
      },
      {
        path: 'test',
        element: <ProtectedRoute><Test /></ProtectedRoute>,
      },
      {
        path: 'profile',
        element: <ProtectedRoute><Profile /></ProtectedRoute>,
      },
      {
        path: 'login',
        element: <Login />,
      },
    ],
  },
]);

const GlobalStyles = createGlobalStyle`
  ${reset};
  * {
    box-sizing: border-box;
  }
  body {
    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    background: ${({ theme }) => theme.backgroundmain};
        color: ${({ theme }) => theme.text};
  }
`;

const Wrapper = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  position: relative;
`;

const TopButton = styled.a`
  width: 40px;
  height: 40px;
  padding-bottom: 3px;
  border-radius: 50%;
  position: fixed;
  bottom: 5%;
  right: 50px;
  font-size: 24px;
  font-weight: 600;
  border: 1px solid;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
  cursor: pointer;
  color: ${({ theme }) => theme.text};
  background: ${({ theme }) => theme.button1};
    border-color: ${({ theme }) => theme.boardBorder2};
  text-decoration: none;
  &:hover {
    color: #ffffff;
    background: #264653;
    transition: .5s;
  }
`;

function App() {
  const [isLoading, setLoading] = useState(true);
  const [theme, setTheme] = useState("light");

  useEffect(() => {
    const init = () => {
      setLoading(false);
    };

    init();
  }, []);

  const handlePage = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <>
      <ThemeProvider theme={theme === "dark" ? light : dark}>
        <Wrapper>
          <GlobalStyles />
          <RouterProvider router={router} />
          <TopButton onClick={handlePage}>↑</TopButton>
          <ThemeToggle></ThemeToggle>
        </Wrapper>
        <ToastContainer
        position="top-center"
        limit={1}
        closeButton={false}
        autoClose={4000}
        hideProgressBar={true}
        bodyStyle={{
          textAlign: 'center',
          color: '#000000',
        }}
      />
      </ThemeProvider>
    </>
  )
}

export default App
