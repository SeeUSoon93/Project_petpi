import { Link } from "react-router-dom";

import styled from "styled-components";

import Footer from "../components/footer";
import Header from "../components/header";
import Animal from "/image/animal-main.png"
import Cogi from "/image/cogi.jpg"
import ImageIcon from "/image/image.png"
import ChartIcon from "/image/chart.png"
import HospitalIcon from "/image/hospital.png"
import MapHospital from "/image/map-hospital.png"
import Calendar from "/image/calendar.png"
import Pets from "/image/pets.jpg"

const Wrapper = styled.div`
    width: 100%;
    height: 100vh;
`;

const WrapperBox = styled.div`
    padding-top: 100px;
    height: auto;
    display: flex;
    padding-top: 100px;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const ContentBox = styled.div`
    width: 100%;
    &.area1{
        height: 600px;
        background: ${({ theme }) => theme.backgrounfix1};
    }
    &.area2,
    &.area6 {
        height: 800px;
        background: ${({ theme }) => theme.background1};
    }
    &.area3 {
        height: 1000px;
    }
    &.area4{
        height: 800px;
        background: ${({ theme }) => theme.backgrounfix1};
    }
    &.area5{
        height: 800px;
    }
    `;

const Content = styled.div`
    max-width: 1400px;
    height: 100%;
    margin: auto;
    display: flex;
    justify-content: space-around;
    align-items: center;
    position: relative;
    gap: 50px;
    &.content3,
    &.content6 {
        justify-content: center;
        flex-direction: column;
    }
`;

const ContentTextArea = styled.div``;

const ContentStepArea = styled.div`
    text-align: center;
    gap: 30px;
`;

const ContentHead = styled.div`
    font-size: 24px;
    font-weight: 600;
    color: #59B0ED;
    padding-bottom: 20px;
`;

const ContentTitle = styled.div`
    font-size: 36px;
    line-height: 1.3;
    padding-bottom: 30px;
`;

const ContentSubTitle = styled.div`
    padding-top: 30px;
    font-size: 24px;
    line-height: 1.3;
    padding-bottom: 30px;
`;

const ContentText = styled.div`
    color: ${({ theme }) => theme.text2};
    line-height: 1.3;
    `;

const ContentButton = styled.div`
    color: ${({ theme }) => theme.buttonText};
    background: ${({ theme }) => theme.background1};
    width: 300px;
    height: 70px;
    padding: 10px 20px;
    margin-top: 50px;
    border: 1px solid;
    border-color: ${({ theme }) => theme.boardBorder1};
    border-radius: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 22px;
    cursor: pointer;
    &.bottom-button {
        margin-top: 0;
        margin-bottom: 100px;
    }
    &:hover {
        transition: 0.3s;
        color: #ffffff;
        background-color: #59B0ED;
    }
`;

const ContentStepBox = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 30px;
`;

const ContentStep = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
`;

const ContentStepBadge = styled.div`
    color: ${({ theme }) => theme.buttonText};
    background: ${({ theme }) => theme.background1};
    width: 150px;
    height: 60px;
    padding: 10px 20px;
    border: 1px solid;
    margin-bottom: 30px;
    border-color: ${({ theme }) => theme.boardBorder1};
    border-radius: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 22px;
`;
const ContentStepImageBox = styled.div`
    width: 400px;
    height: 400px;
    border-radius: 50%;
    background-color: white;
    display: flex;
    justify-content: center;
    align-items: center;
`;
const ContentStepImage = styled.img`
    width: 300px;
    &.step-image3 {
        width: 250px;
    }
`;

const ContentTopImage = styled.img``;

const ContentMiddleImage = styled.img`
    width: 700px;
    border-radius: 20px;
`;

const ContentMapImage = styled.img`
    width: 600px;
    height: 600px;
    border-radius: 50%;
`;

const ContentCalendarImage = styled.img`
    width: 600px;
    height: 600px;
    border-radius: 20px;
`;

const ContentBottomImage = styled.img`
    width: 900px;
`;

export default function Home() {

    return (
        <Wrapper>
            <Header></Header>
            <WrapperBox>
                <ContentBox className="area1">
                    <Content className="content1">
                        <ContentTopImage src={Animal} />
                        <ContentTextArea>
                            <ContentTitle>
                                소중한 반려동물들의 건강!<br />
                                항상 걱정되시나요?
                            </ContentTitle>
                            <ContentText>
                                사진으로 알아보는 우리아이 피부질환 확인하기
                            </ContentText>
                            <Link to="test" style={{ textDecoration: "none" }}>
                                <ContentButton>
                                    피부질환 검사하기 &gt;
                                </ContentButton>
                            </Link>
                        </ContentTextArea>
                    </Content>
                </ContentBox>
                <ContentBox className="area2">
                    <Content className="content2">
                        <ContentTextArea>
                            <ContentHead>
                                피부질환 체크
                            </ContentHead>
                            <ContentTitle>
                                무심코 지나친 가벼운 상처<br />
                                꼭 확인해봐야 합니다
                            </ContentTitle>
                            <ContentText>
                                우리아이가 놀다가 긁힌거 같은데<br />
                                많이 아픈건 아닐까 걱정돼요.<br /><br /><br />
                                코기맘 ( 3세 / 여 / 웰시코기 )
                            </ContentText>
                        </ContentTextArea>
                        <ContentMiddleImage src={Cogi} />
                    </Content>
                </ContentBox>
                <ContentBox className="area3">
                    <Content className="content3">
                        <ContentStepArea>
                            <ContentHead>
                                피부질환 AI검사
                            </ContentHead>
                            <ContentTitle>
                                집에서 AI검사로 쉽고 빠르게 해보세요
                            </ContentTitle>
                        </ContentStepArea>
                        <ContentStepBox>
                            <ContentStep>
                                <ContentStepBadge>Step 1</ContentStepBadge>
                                <ContentStepImageBox>
                                    <ContentStepImage src={ImageIcon} />
                                </ContentStepImageBox>
                                <ContentSubTitle>피부 사진등록</ContentSubTitle>
                            </ContentStep>
                            <ContentTitle> ⟩ </ContentTitle>
                            <ContentStep>
                                <ContentStepBadge>Step 2</ContentStepBadge>
                                <ContentStepImageBox>
                                    <ContentStepImage src={ChartIcon} />
                                </ContentStepImageBox>
                                <ContentSubTitle>결과확인</ContentSubTitle>
                            </ContentStep>
                            <ContentTitle> ⟩ </ContentTitle>
                            <ContentStep>
                                <ContentStepBadge>Step 3</ContentStepBadge>
                                <ContentStepImageBox>
                                    <ContentStepImage className="step-image3" src={HospitalIcon} />
                                </ContentStepImageBox>
                                <ContentSubTitle>가까운 병원출력</ContentSubTitle>
                            </ContentStep>
                        </ContentStepBox>
                    </Content>
                </ContentBox>
                <ContentBox className="area4">
                    <Content className="content4">
                        <ContentMapImage src={MapHospital} />
                        <ContentTextArea>
                            <ContentHead>
                                동물병원 알리미
                            </ContentHead>
                            <ContentTitle>
                                내 주변 에서 가까운 동물병원을<br />
                                한눈에 볼 수 있어요
                            </ContentTitle>
                            <ContentText>
                                현재위치 기반으로 가까운 동물병원을 지도에 표시하여 알려드려요<br />
                                검사를 진행하지 않아도 찾아볼 수 있어요!
                            </ContentText>
                        </ContentTextArea>
                    </Content>
                </ContentBox>
                <ContentBox className="area5">
                    <Content className="content5">
                        <ContentTextArea>
                            <ContentHead>
                                반려동물  스케줄 관리
                            </ContentHead>
                            <ContentTitle>
                                반려동물의 일상을 기록하고<br />
                                일정을 정리하세요
                            </ContentTitle>
                            <ContentText>
                                이전의 기록이나 앞으로의 일정을 캘린더에 기록하세요<br />
                                병원의 예약기록이나 소중한 추억을 담을 수 있어요!
                            </ContentText>
                        </ContentTextArea>
                        <ContentCalendarImage src={Calendar} />
                    </Content>
                </ContentBox>
                <ContentBox className="area6">
                    <Content className="content6">
                        <ContentBottomImage src={Pets} />
                            <ContentTitle>
                                현명한 반려인의 건강체크, 펫피
                            </ContentTitle>
                            <Link to="test" style={{ textDecoration: "none" }}>
                                <ContentButton className="bottom-button">
                                    건강체크 시작하기 &gt;
                                </ContentButton>
                            </Link>
                    </Content>
                </ContentBox>
            </WrapperBox>
            <Footer></Footer>
        </Wrapper>
    )
}