import React from 'react';
import { Navigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function ProtectedRoute({ children }) {
  const userDataString = sessionStorage.getItem('userData');
  const notify = () => toast.error('로그인이 필요합니다!');

  if (!userDataString) {
    notify();
    return (
      <>
        <Navigate to="/login" />
      </>
    );
  }

  return children;
}
