# TASK QUEUE
```text
- Task: [start] -> [extract] -> [load] -> [end]
- Vendor: [extract], [load]
- Bài toán:
    + Một vendor có hai phần extract và load
    + Một task thì sẽ thực hiện 4 việc start, extract, load và end
    + Tuy nhiên 1 vendor thì chỉ chạy 1 việc tại một thời điểm
```

```log
2025-04-25T19:47:13.927+09:00  INFO 91195 --- : taskId=[0] START: CAFE24 -> KAKAO
2025-04-25T19:47:13.930+09:00  INFO 91195 --- : taskId=[0] Cafe24Extractor started wait 2s
2025-04-25T19:47:13.930+09:00  INFO 91195 --- : taskId=[1] START: KAKAO -> CAFE24
2025-04-25T19:47:13.931+09:00  INFO 91195 --- : taskId=[2] START: CAFE24 -> KAKAO
2025-04-25T19:47:13.931+09:00  INFO 91195 --- : taskId=[1] KakaoExtractor started wait 4s
2025-04-25T19:47:15.934+09:00  INFO 91195 --- : taskId=[0] Cafe24Extractor ended
2025-04-25T19:47:15.935+09:00  INFO 91195 --- : taskId=[2] Cafe24Extractor started wait 8s
2025-04-25T19:47:17.936+09:00  INFO 91195 --- : taskId=[1] KakaoExtractor ended
2025-04-25T19:47:17.937+09:00  INFO 91195 --- : taskId=[0] KakaoLoader started wait 7s
2025-04-25T19:47:23.940+09:00  INFO 91195 --- : taskId=[2] Cafe24Extractor ended
2025-04-25T19:47:23.941+09:00  INFO 91195 --- : taskId=[1] Cafe24Loader started wait 6s
2025-04-25T19:47:24.942+09:00  INFO 91195 --- : taskId=[0] KakaoLoader ended
2025-04-25T19:47:24.944+09:00  INFO 91195 --- : taskId=[0] END 0 in 11024 ms
2025-04-25T19:47:24.945+09:00  INFO 91195 --- : taskId=[2] KakaoLoader started wait 3s
2025-04-25T19:47:27.945+09:00  INFO 91195 --- : taskId=[2] KakaoLoader ended
2025-04-25T19:47:27.947+09:00  INFO 91195 --- : taskId=[2] END 2 in 14017 ms
2025-04-25T19:47:29.946+09:00  INFO 91195 --- : taskId=[1] Cafe24Loader ended
2025-04-25T19:47:29.948+09:00  INFO 91195 --- : taskId=[1] END 1 in 16018 ms
```
