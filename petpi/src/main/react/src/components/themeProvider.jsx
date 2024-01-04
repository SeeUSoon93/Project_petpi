import useTheme from "../components/useTheme";
import React, { ReactNode, createContext } from "react";
import { light, dark } from "../components/theme";
import { ThemeProvider as StyledThemeProvider } from "styled-components";

const defaultValue = {
  theme: "light",
  onChangeTheme: () => {},
};

export const ThemeContext = createContext(defaultValue);

function ThemeProvider({ children }) {
  const themeProps = useTheme();

  return (
    <ThemeContext.Provider value={themeProps}>
      <StyledThemeProvider theme={themeProps.theme === "light" ? light : dark}>
        {children}
      </StyledThemeProvider>
    </ThemeContext.Provider>
  );
}

export default ThemeProvider;