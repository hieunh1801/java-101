# TableauIntegrationApplication
- Hướng dẫn tích hợp với TableauCloud và TableauServer

```text
Yêu cầu: 
- [x] Rate limitation
- [x] Token Issues
    - [x] Các API get users, get groups phải có đính kèm token
    - [x] Token issues trong vòng 15 phút, quá 15 phút thì issues lại token
    - [x] Khi token hết hạn thì phải tự động retry lại 3 lần. Quá 3 lần thì thôi.
- [x] Get resource với paging
```