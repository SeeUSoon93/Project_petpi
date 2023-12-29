import { Link } from "react-router-dom";

import styled, { css } from "styled-components";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import MainLogo from "/image/pet-logo.png"

const Wrapper = styled.div`
    width: 100%;
    height: 100px;
    margin: auto;
    background: ${({ theme }) => theme.header};
    color: ${({ theme }) => theme.text};
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    white-space: nowrap;
    position: fixed;
    top: 0;
    box-shadow: 5px 5px 50px 5px #99999944;
    z-index: 199;
`;

const WrapperBox = styled.div`
    width: 100%;
    max-width: 1400px;
    height: 100px;
    padding: 0 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const TitleBox = styled.div`
    height: 100px;
    display: flex;
    align-items: center;
    gap: 20px;
`;

const TitleLogo = styled.img`
    width: 100px;
`;

const TitleText = styled.div`
    height: 100px;
    display: flex;
    justify-content: center;
    flex-direction: column;
    gap: 5px;
`;

const TitleMain = styled.div`
    color: ${({ theme }) => theme.text};
    font-size: 24px;
    display: flex;
    gap: 8px;
`;

const TitleMainPoint = styled.div`
    color: #59B0ED;
`;

const TitleSub = styled.div`
    color: ${({ theme }) => theme.text};
    font-size: 10px;
`;

const MenuBox = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 100px;
`;

const MenuItem = styled.div`
    color: ${({ theme }) => theme.text};
    &:hover {
        color: #59B0ED;
    }
`;

const UserBox = styled.div`
    width: 200px;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;
`;

const LoginButton = styled.div`
    color: ${({ theme }) => theme.text};
    padding: 10px 20px;
    cursor: pointer;
    &:hover {
        color: #59B0ED;
    }
`;


export default function Header() {
    return (
        <Wrapper>
            <WrapperBox>
                <Link to="/" style={{ textDecoration: "none" }}>
                    <TitleBox>
                        <TitleLogo src={MainLogo} />
                        <TitleText>
                            <TitleMain>
                                <TitleMainPoint>PET </TitleMainPoint>
                                PI
                            </TitleMain>
                            <TitleSub>PET HEALTH CARE</TitleSub>
                        </TitleText>
                    </TitleBox>
                </Link>
                <MenuBox>
                    <Link to="/" style={{ textDecoration: "none" }}>
                        <MenuItem>펫피소개</MenuItem>
                    </Link>
                    <Link to="map" style={{ textDecoration: "none" }}>
                        <MenuItem>시설지도</MenuItem>
                    </Link>
                    <Link to="test" style={{ textDecoration: "none" }}>
                        <MenuItem>피부질환 검사</MenuItem>
                    </Link>
                    <Link to="profile" style={{ textDecoration: "none" }}>
                        <MenuItem>마이페이지</MenuItem>
                    </Link>
                </MenuBox>
                <Link to="login" style={{ textDecoration: "none" }}>
                    <UserBox>
                        <LoginButton>로그인</LoginButton>
                    </UserBox>
                </Link>
            </WrapperBox>
        </Wrapper>
    )
}