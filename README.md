# triple

## 구동 방법
#### 1. mysql을 설치해주세요.
#### 2. 이후 API 사용 방법은 triple API.postman_collection.json 파일을 참고해주세요.
- postman으로 export 하셔도 좋습니다.
#### 3. 간단한 사용방법은 이러합니다.
1. 사용자 생성 : `POST /member`
```
{
    "memberId" : "3ede0ef2-92b7-4817-a5f3-0c575361f745"
}
```
2. 장소 생성 : `POST /place`
```
{
    "placeId" : "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
3. 리뷰 생성 : `POST /review`
```
{
    "type": "REVIEW",
    "action": "ADD"
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
    "content": "좋아요!",
    "attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],
    "memberId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
    "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
4. 리뷰 수정 : `POST /review`
```
{
    "type": "REVIEW",
    "action": "MOD", 
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
    "content": "",
    "attachedPhotoIds": []
}
```
5. 리뷰 삭제 : `POST /review`
```
{
    "type": "REVIEW",
    "action": "DELETE", 
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
}
```
6. 사용자 정보 확인(포인트까지) : `GET /member/{memberId}`
```
GET /member/3ede0ef2-92b7-4817-a5f3-0c575361f745
```
