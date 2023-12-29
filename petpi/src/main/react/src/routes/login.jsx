import { Link } from "react-router-dom";

import styled from "styled-components";

import Background from "/image/login-back.jpg"
import MainLogo from "/image/pet-logo.png"
import Kakao from "/kakaotalk-logo.svg"

const Wrapper = styled.div`
    width: 100%;
    height: 100vh;
    position: relative;
    background-color: #ffffff;
`;

const BackgroundImage = styled.img`
    width: 100%;
    overflow: hidden;
    position: absolute;
    bottom: 0;
`;

const LoginBox = styled.div`
    width: 400px;
    height: 500px;
    background: ${({ theme }) => theme.backgrounfix2};
    margin-top: 200px;
    margin-left: 10%;
    padding: 50px;
    border: 1px solid #99999944;
    border-radius: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    position: absolute; 
`;

const TitleBox = styled.div`
    width: 250px;
    height: 100px;
    display: flex;
    align-items: center;
    gap: 20px;
    margin-bottom: 50px;
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

const TextBox = styled.div`
    padding-bottom: 50px;
    text-align: center;
    font-size: 20px;
    line-height: 1.3 ;
`;

const TextHead = styled.div`
    font-size: 22px;
    padding: 20px 0;
`;

const KakaoButton = styled.div`
    width: 280px;
    height: 60px;
    border-radius: 10px;
    font-weight: 600;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #000000;
    background-color: #FFE812;
    cursor: pointer;
    gap: 10px;
`;

const KakaoLogo = styled.img`
    width: 40px;
    height: 40px;
`;

export default function Login() {

    return (
        <>
            <Wrapper>
                <BackgroundImage src={Background} />
                <LoginBox>
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
                    <TextBox>
                        <TextHead>어서오세요!</TextHead>

                        반려인의 걱정을 덜어주는<br />
                        펫피 입니다.
                    </TextBox>
                    <KakaoButton>
                        <KakaoLogo src={Kakao} />
                        카카오톡으로 시작하기
                    </KakaoButton>
                </LoginBox>
            </Wrapper>
        </>
    )
}