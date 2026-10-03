# QLTSTB

Ứng dụng **Quản lý Tài sản** viết bằng **Java Swing**, kết nối cơ sở dữ liệu **SQL Server**.
Dự án dùng kiến trúc 3 tầng: `DAO` (truy cập CSDL) → `BUS` (nghiệp vụ) → `View` (giao diện).

---

## 1. Yêu cầu môi trường

| Công nghệ | Phiên bản | Ghi chú |
|---|---|---|
| JDK | 17 trở lên | Dự án biên dịch với `--release 17` |
| SQL Server | 2019 trở lên | Đã kiểm thử trên SQL Server 2025 |
| NetBeans IDE | 26 trở lên | Bắt buộc có để sinh `nbproject/build-impl.xml` |
| Apache Ant | *(không bắt buộc)* | Chỉ cần nếu muốn build bằng dòng lệnh |

Driver JDBC **đã được commit sẵn** trong `lib/mssql-jdbc-13.4.0.jre11.jar`, không cần tải thêm.

---

## 2. Cấu trúc dự án

```
QLTSTB/
├── build.xml                     # Script Ant (import build-impl.xml của NetBeans)
├── lib/
│   └── mssql-jdbc-13.4.0.jre11.jar   # Driver JDBC SQL Server
├── nbproject/
│   ├── project.xml               # Định nghĩa project (NetBeans)
│   ├── project.properties        # Cấu hình biên dịch + thư viện
│   ├── build-impl.xml            # ⚠ FILE SINH TỰ ĐỘNG – không commit, không sửa
│   ├── genfiles.properties       # ⚠ FILE SINH TỰ ĐỘNG
│   └── private/                  # ⚠ Cấu hình riêng của máy – không commit
└── src/com/company/qlts/
    ├── Main.java                 # Điểm khởi chạy → mở LoginFrame
    ├── bus/                      # Tầng nghiệp vụ
    ├── common/                   # AppColor, Session
    ├── component/                # Swing component dùng chung
    ├── config/DBConnection.java  # Cấu hình kết nối SQL Server
    ├── dao/                      # Tầng truy cập CSDL
    ├── entity/                   # Lớp thực thể
    ├── util/                     # MessageUtil, UiUtil
    └── view/
        ├── login/                # Màn hình đăng nhập
        ├── main/                 # MainFrame, Sidebar, Dashboard
        └── taissan/              # Các màn hình quản lý (panel + dialog)
```

---

## 3. Cài đặt

### Bước 1 — Lấy mã nguồn

```bash
git clone <đường-dẫn-repo> QLTSTB
cd QLTSTB
```

### Bước 2 — Tạo cơ sở dữ liệu

Tạo database tên **`qlts_db`** trên SQL Server. CSDL gồm **16 bảng**:

| Nhóm | Bảng |
|---|---|
| Tài sản | `tai_san`, `loai_tai_san`, `thiet_bi` |
| Tài khoản | `tai_khoan`, `phan_quyen`, `nhan_vien`, `phong_ban` |
| Đối tác | `nha_cung_cap` |
| Nghiệp vụ | `khau_hao`, `kiem_ke`, `thanh_ly`, `dieu_chuyen`, `bao_tri`, `cap_phat`, `nhat_ky` |

> **Lưu ý:** hiện repo **chưa có file `.sql`** để tạo dữ liệu.
> Hãy dùng database có sẵn của bạn, hoặc xem phần [7. Ghi chú](#7-ghi-chú-quan-trọng).

### Bước 3 — Sửa thông tin kết nối

Mở `src/com/company/qlts/config/DBConnection.java`, sửa cho khớp máy bạn:

```java
private static final String URL  = "jdbc:sqlserver://localhost:1433;"
                                 + "databaseName=qlts_db;"
                                 + "encrypt=true;trustServerCertificate=true;";
private static final String USER = "sa";         // ← tài khoản SQL Server
private static final String PASS = "123456789";  // ← mật khẩu SQL Server
```

### Bước 4 — Mở bằng NetBeans

1. Mở NetBeans → **File ▸ Open Project** → chọn thư mục `QLTSTB`.
2. NetBeans sẽ **tự sinh** `nbproject/build-impl.xml` nếu thiếu.
3. Chờ thanh dưới cùng báo *Project build succeeded*.

---

## 4. Cách chạy

### Cách 1 — Trên NetBeans (khuyến nghị)

- Chuột phải `Main.java` → **Run File** (hoặc `Shift+F6`).
- Muốn kiểm tra kết nối CSDL mà không mở giao diện:
  chạy `src/com/company/qlts/config/DBConnection.java`.
  Nếu thấy dòng `Kết nối thành công: qlts_db` là OK.

### Cách 2 — Dùng Apache Ant

```bash
ant standalone-compile    # biên dịch src/  → build/classes
ant run-app              # biên dịch rồi chạy
ant standalone-jar       # tạo dist/QLTSTB-1.0.jar
ant run-app-jar          # chạy từ file jar
```

> `build.xml` import `nbproject/build-impl.xml`. Nếu báo lỗi không tìm thấy file này,
> hãy mở project bằng NetBeans một lần để sinh ra rồi chạy lại `ant`.

### Cách 3 — Không có Ant / NetBeans (dùng `javac` trực tiếp)

```powershell
# Gom danh sách file .java
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } | Out-File -Encoding ascii sources.txt

# Biên dịch
javac -encoding UTF-8 --release 17 -d build/classes -cp lib/mssql-jdbc-13.4.0.jre11.jar @sources.txt

# Chạy
java -cp "build/classes;lib/mssql-jdbc-13.4.0.jre11.jar" com.company.qlts.Main
```

> Cần đặt `javac`/`java` trong `PATH`, hoặc gọi đường dẫn đầy đủ
> `"C:\Program Files\Java\jdk-26\bin\javac.exe"`.

---

## 5. Tài khoản đăng nhập

| Tài khoản | Mật khẩu | Họ tên | Vai trò (`ma_quyen`) |
|---|---|---|---|
| `admin` | `123456` | Quản trị viên | 1 – ADMIN |
| `quanly1` | `123456` | Nguyễn Văn Cường | 2 – QUAN_LY |
| `nv1` | `123456` | Lê Đức Bình | 3 – NHAN_VIEN |

> Hiện cả 3 vai trò đều thấy cùng một menu (chưa phân quyền theo vai trò).

---

## 6. Cấu trúc cơ sở dữ liệu (rút gọn)

Các bảng chính và khóa liên kết:

```
phan_quyen (ma_quyen)
      ▲
tai_khoan ──► nhan_vien ──► phong_ban
      │
nha_cung_cap ──► tai_san ──► loai_tai_san
                     │
                     ├──► thiet_bi
                     ├──► khau_hao
                     ├──► kiem_ke
                     ├──► thanh_ly
                     ├──► dieu_chuyen
                     └──► bao_tri / cap_phat / nhat_ky
```

Một số cột đáng chú ý:

| Bảng | Cột chính |
|---|---|
| `tai_khoan` | `ma_tai_khoan`, `ten_dang_nhap`, `mat_khau`, `ho_ten`, `ma_quyen`, `trang_thai` |
| `tai_san` | `ma_tai_san`, `ma_ts_code` (`TS0001`…), `ten_tai_san`, `gia_mua`, `gia_tri_hien_tai`, `trang_thai` |

---

## 7. Ghi chú quan trọng

### ⚠ File do NetBeans sinh ra – KHÔNG commit

| File | Lý do |
|---|---|
| `nbproject/build-impl.xml` | Sinh từ `project.xml` + `project.properties`, dung lượng ~99 KB |
| `nbproject/genfiles.properties` | Bookkeeping để NetBeans phát hiện file sinh ra đã cũ |
| `nbproject/private/` | Chứa đường dẫn tuyệt đối của máy (`C:\Users\...\NetBeans\31\`) |

Các file này đã được thêm vào `.gitignore`. NetBeans sẽ tự sinh lại khi mở project.
Hệ quả: **clone mới phải mở bằng NetBeans một lần** trước khi dùng được lệnh `ant`.

### ⚠ Driver JDBC được commit

`lib/mssql-jdbc-13.4.0.jre11.jar` (1.48 MB) **có chủ đích** được commit, để clone mới build
được ngay mà không cần tải thêm. `.gitignore` đã bảo vệ file này khỏi bị ignore nhầm.

### ⚠ Mật khẩu đang lưu dạng văn bản thuần

Cột `tai_khoan.mat_khau` chứa mật khẩu chưa mã hóa, và thông tin đăng nhập SQL Server
đang ghi cứng trong `DBConnection.java`. Chỉ phù hợp với môi trường học tập/demo —
**không dùng cho triển khai thật**.

### ⚠ Cột `trang_thai` chưa được kiểm tra khi đăng nhập

`TaiKhoanBUS.checkLogin()` chỉ so khớp tên đăng nhập + mật khẩu, **không** kiểm tra
`trang_thai` (tài khoản có bị khóa hay không). Dữ liệu hiện có cả 3 tài khoản đều
`trang_thai = 1`. Nếu muốn chặn tài khoản bị khóa, thêm điều kiện vào `checkLogin()`.

### ⚠ Cảnh báo khi biên dịch

Khi biên dịch có thể gặp ~23 cảnh báo `this-escape` (do Swing khởi tạo component trong
constructor). Đây là **cảnh báo, không phải lỗi** — code vẫn chạy bình thường.

### ⚠ Thiếu script tạo CSDL

Repo chưa có file `.sql` mô tả schema + dữ liệu mẫu của `qlts_db`.
Hãy tạo bằng SQL Server Management Studio rồi export ra, hoặc dùng script có sẵn của bạn.

---

## 8. Câu hỏi thường gặp

**Lỗi `ClassNotFoundException: com.microsoft.sqlserver.jdbc.SQLServerDriver`**
→ Thiếu driver trong classpath. Kiểm tra file `lib/mssql-jdbc-13.4.0.jre11.jar` có tồn tại không.

**Lỗi `bad path element ... file.reference.mssql-jdbc`**
→ Thuộc tính classpath bị hỏng trong `project.properties`. Giữ nguyên cấu hình classpath
định nghĩa trong `build.xml` (dòng 26–37), không sửa `javac.classpath` trong `project.properties`.

**Lỗi `Login failed for user 'sa'`**
→ Sai thông tin đăng nhập SQL Server trong `DBConnection.java`, hoặc SQL Server đang bật
Windows Authentication. Xem [Bước 3](#bước-3--sửa-thông-tin-kết-nối).

**Lỗi `Object not defined` / không đổi tiếng Việt có dấu**
→ Đảm bảo đã dùng `-encoding UTF-8` khi biên dịch (mặc định trong `build.xml` đã có).

**Ant báo không tìm thấy `nbproject/build-impl.xml`**
→ Mở project bằng NetBeans một lần để sinh file đó. Xem [mục 7](#-file-do-netbeans-sinh-ra--không-commit).

---

## 9. Công nghệ sử dụng

- Java Swing (UI)
- JDBC `mssql-jdbc` (kết nối SQL Server)
- Apache Ant + NetBeans (build)
- Kiến trúc 3 tầng: DAO / BUS / Entity