import { Link, useNavigate } from "react-router-dom";

import styled from "styled-components";
import { toast } from 'react-toastify';

import Background from "/image/login-back.jpg"
import MainLogo from "/image/pet-logo.png"
import Kakao from "/kakaotalk-logo.svg"
import { useState } from "react";

const Wrapper = styled.div`
    width: 100%;
    height: 100vh;
    position: relative;
    background-color: #ffffff;
      /* &.active {
    .Login-box {
        visibility: visible;
    }
    .signup-box {
        visibility: hidden;
    }
  } */
`;

const BackgroundImage = styled.img`
    width: 100%;
    overflow: hidden;
    position: absolute;
    bottom: 0;
`;

const LoginBox = styled.form`
    width: 400px;
    height: 600px;
    background: ${({ theme }) => theme.backgrounfix2};
    margin-top: 150px;
    margin-left: 10%;
    padding: 50px;
    border: 1px solid #99999944;
    border-radius: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    position: absolute; 
    gap: 20px;
    visibility: ${(props) => (props.isActive ? 'visible' : 'hidden')};
`;

const TitleBox = styled.div`
    width: 250px;
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

const TextBox = styled.div`
    text-align: center;
    font-size: 20px;
    line-height: 1.3 ;
`;

const TextHead = styled.div`
    font-size: 22px;
    padding: 20px 0;
`;

const UserInput = styled.input`
  border: none;
  border-bottom: 2px solid #D1D1D4;
  color: ${({ theme }) => theme.text};
  background: none;
  padding: 20px;
  width: 100%;
  height: 20px;
  transition: .2s;
  &:active,
  &:focus,
  &:hover {
    outline: none;
    border-color: #59B0ED;
  }
  &:focus {
    color: ${({ theme }) => theme.text};
  }
  &::placeholder {}
`;

const CheckBoxWrap = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    white-space: nowrap;
    gap: 20px;
`;

const CheckInput = styled.input`
    width: 20px;
    height: 20px;
    /* display:none; */
`;

const CheckLabel = styled.label`
    cursor: pointer;
    &:before {
        content: "";
        display: inline-block;
        width: 17px;
        height: 17px;
        border: 2px solid #F47C7C;
        border-radius: 4px;
        vertical-align: middle;
    }
    &.userRole:checked + label:before {
        content: '✔';
        background-color: #F47C7C;
        border-color: #F47C7C;
        background-position: 50%;
    }
`;

const SubmitButton = styled.input`
    width: 150px;
    height: 50px;
    border: 2px solid #59B0ED;
    color: #59B0ED;
    border-radius: 10px;
    font-size: 18px;
    text-align: center;
    outline: none;
    background: none;
    cursor: pointer;
    &:hover {
        transition: .5s;
        color: #ffffff;
        background-color: #59B0ED;
    }
`;

const BottomText = styled.div`
  text-align: center;
  text-transform: uppercase;
`;

const LinkBox = styled.a`
  text-decoration: none;
  font-weight: bold;
  color: #59B0ED;
  &:is(:visited) {
    color: #59B0ED;
  }
  &:hover{
    color: #264653;
    transition: .5s;
  }
`;

const SocialButtonBox = styled.div`
    width: 100%;
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: 20px 0;
`;

const SocialButton = styled.img`
    width: 40px;
    height: 40px;
    border-radius: 10px;
    padding: 4px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    &.KakaoButton {
        color: #000000;
        background-color: #FFE812;
    }
`;

const KakaoLogo = styled.img`
    width: 40px;
    height: 40px;
`;

export default function Login() {
    const [isActive, setIsActive] = useState(false);
    const [signupFormData, setSignupFormData] = useState({
        userEmail: '',
        userPw: '',
        userNick: '',
        userRole: '',
    });
    const [loginFormData, setLoginFormData] = useState({
        userEmail: '',
        userPw: '',
    });
    const navigate = useNavigate();


    // 중복 확인 함수
    const checkDuplicateEmail = async () => {
        const email = signupFormData.userEmail;
        const url = `/api/users/duplication-email/${email}`;
        try {
            const response = await axios.get(url);
            const responseData = response.data;
            console.log(responseData);
            if (responseData == true) {
                setIsDuplicateEmail(true);
                console.log("이메일 중복");
                setIsEmail(true);
            } else if (responseData == false) {
                setIsDuplicateEmail(false);
                console.log("사용가능한 이메일")
            } else {
                console.log("이메일 형식이 아닙니다.");
                setIsDuplicateEmail(true);
                setIsEmail(false);
            }
        } catch (error) {
            console.error('중복 확인 에러:', error);
        }
    };

    const checkDuplicatePhone = async () => {
        const phone = signupFormData.userPhone;
        const url = `/api/users/duplication-phone/${phone}`;
        try {
            const response = await axios.get(url);
            const responseData = response.data;

            if (responseData === true) {
                setIsDuplicatePhone(true);
                console.log("핸드폰번호 중복");
            } else {
                setIsDuplicatePhone(false);
                console.log("사용가능한 핸드폰번호")
            }
        } catch (error) {
            console.error('중복 확인 에러:', error);
        }
    };

    const checkDuplicateName = async () => {
        const userName = signupFormData.userNick;
        const url = `/api/users/duplication-userName/${userNick}`;
        try {
            const response = await axios.get(url);
            const responseData = response.data;

            if (responseData === true) {
                setIsDuplicateName(true);
                console.log("이미 사용중이 이름 중복");
            } else {
                setIsDuplicateName(false);
                console.log("사용가능한 이름")
            }
        } catch (error) {
            console.error('중복 확인 에러:', error);
        }
    };

    const toggle = () => {
        setIsActive((prevIsActive) => !prevIsActive);
        // 회원가입 폼의 입력값 초기화
        setSignupFormData({
            userEmail: '',
            userPw: '',
            userNick: '',
            userRole: '',
        });
        // 로그인 폼의 입력값 초기화
        setLoginFormData({
            userEmail: '',
            userPw: '',
        });
    };

    // 로그인 폼을 제출할 때 실행되는 함수
    const handleLoginSubmit = (e) => {
        e.preventDefault();

        // 비밀번호와 확인 비밀번호를 비교하여 일치 여부 확인
        if (signupFormData.userPw !== signupFormData.passwordCheck) {
            setIsDuplicatePw(true);
            return;
        }

        axios.post('/api/users/loginuser', loginFormData)
            .then(response => {
                console.log('로그인 응답 받음:', response.data);
                const userEmail = response.data.userEmail;
                if (!response.data) {
                    const notify = () => toast.error('아이디와 비밀번호를 확인해주세요');
                    notify();
                    return;
                }
                setIsLoggedIn(true);
                const notify = () => toast.success('로그인 성공!');
                notify();
                axios.get(`/api/users/${userEmail}`)
                    .then(userResponse => {
                        console.log('유저 정보 응답 받음:', userResponse.data);
                        const userDataString = JSON.stringify(userResponse.data);
                        sessionStorage.setItem('userData', userDataString);
                        navigate('/');
                    })
                    .catch(userError => {
                        console.error('유저 정보 요청 에러 발생:', userError);
                    })
            })
            .catch(error => {
                console.error('로그인 에러 발생:', error);
            });

    };

    // 회원가입 폼을 제출할 때 실행되는 함수
    const handleSignupSubmit = (e) => {
        e.preventDefault();

        axios.post('/api/users/join', signupFormData)
            .then(response => {
                console.log('회원가입 응답 받음:', response.data);
                const notify = () => toast.success('회원가입 성공!');
                notify();
                toggle();
            })
            .catch(error => {
                const notify = () => toast.error('회원가입 실패');
                notify();
                console.error('회원가입 에러 발생:', error);
            });
    };
    return (
        <>
            <Wrapper className={isActive ? 'active' : ''}>
                <BackgroundImage src={Background} />
                <LoginBox isActive={isActive} className="signup-box" onSubmit={handleSignupSubmit}>
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
                    <UserInput type="email" name="userId" placeholder="Email" />
                    <UserInput type="password" name="userPw" placeholder="Password" />
                    <UserInput type="password" name="userPwCheck" placeholder="Password" />
                    <UserInput type="text" name="userNick" placeholder="Nickname" />
                    <CheckBoxWrap>
                        <CheckInput type="checkbox" name="userRole" id="userRole" 
                            checked={signupFormData.userRole === '1'}
                            onChange={(e) => {
                                // 체크 여부에 따라 1(예) 또는 0(아니오)으로 값을 변경
                                const value = e.target.checked ? '1' : '0';
                                setSignupFormData((prevFormData) => ({
                                    ...prevFormData,
                                    userRole: value,
                                }));
                            }} />
                        수의사이신가요?
                        {/* <CheckLabel htmlFor="userRole">수의사이신가요?</CheckLabel> */}
                    </CheckBoxWrap>
                    <SubmitButton type="submit" value="Login" />
                    <BottomText>
                        가입한 아이디가 있으신가요? &nbsp;
                        <LinkBox href="#" onClick={() => toggle()}>로그인</LinkBox>
                    </BottomText>
                </LoginBox>
                <LoginBox isActive={!isActive} className="Login-box" onSubmit={handleLoginSubmit}>
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
                    <UserInput type="email" name="userId" placeholder="Email" />
                    <UserInput type="password" name="userPassword" placeholder="Password" />
                    <SubmitButton type="submit" value="Login" />
                    <SocialButtonBox>
                        <SocialButton className="KakaoButton" src={Kakao} />
                        <SocialButton className="KakaoButton" src={Kakao} />
                        <SocialButton className="KakaoButton" src={Kakao} />
                    </SocialButtonBox>
                    <BottomText>
                        가입한 아이디가 없으신가요? &nbsp;
                        <LinkBox href="#" onClick={() => toggle()}>회원가입</LinkBox>
                    </BottomText>
                </LoginBox>
            </Wrapper>
        </>
    )
}