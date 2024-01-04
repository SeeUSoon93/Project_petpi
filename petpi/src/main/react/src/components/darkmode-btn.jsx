import React, { useContext } from 'react';
import styled from 'styled-components';
import { ThemeContext } from './themeProvider';


const ToggleWrapper = styled.button`
  position: fixed;
  z-index: 999999;
  bottom: 5%;
  right: 120px;
  font-size: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 1px solid;
  padding: 0 0 3px 1px;
  color: ${({ theme }) => theme.text};
  background: ${({ theme }) => theme.button1};
  border-color: ${({ theme }) => theme.boardBorder2};
  cursor: pointer;
  &:hover {
    color: #ffffff;
  background: #264653;
  transition: .5s;
  }
`;



function ThemeToggle() {
  const { theme, onChangeTheme } = useContext(ThemeContext);
  return (
    <ToggleWrapper onClick={onChangeTheme}>
      {theme === 'light' ? "☀️" : "🌙"}
    </ToggleWrapper>
  );
}

export default ThemeToggle;
