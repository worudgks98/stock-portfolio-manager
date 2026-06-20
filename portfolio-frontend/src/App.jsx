import { useEffect, useState } from "react";
import axios from "axios";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  Legend,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid
} from "recharts";

const COLORS = [
  "#0088FE",
  "#00C49F",
  "#FFBB28",
  "#FF8042",
  "#8884D8",
  "#82CA9D"
];

function App() {

  const [stocks, setStocks] = useState([]);

  const [stockName, setStockName] = useState("");
  const [ticker, setTicker] = useState("");
  const [buyPrice, setBuyPrice] = useState("");
  const [currentPrice, setCurrentPrice] = useState("");
  const [quantity, setQuantity] = useState("");
  const [editingId, setEditingId] = useState(null);
  const [keyword, setKeyword] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [loginEmail, setLoginEmail] = useState("");
  const [loginPassword, setLoginPassword] = useState("");
  const [memberName, setMemberName] = useState("");
  const memberId = localStorage.getItem("memberId");

  const loadStocks = () => {

    const memberId =
      localStorage.getItem("memberId");

    axios
      .get(
        `http://localhost:8080/api/stocks/${memberId}`
      )
      .then((response) => {
        setStocks(response.data);
      });
  };

  useEffect(() => {

    const name =
      localStorage.getItem("memberName");

    if(name){
      setMemberName(name);
    }

  }, []);

  useEffect(() => {

    if (!ticker) {
      return;
    }

    loadCurrentPrice();

  }, [ticker]);

  useEffect(() => {
    loadStocks();
  }, []);

  const deleteStock = (id) => {
        axios
          .delete(`http://localhost:8080/api/stocks/${id}`)
          .then(() => {
            loadStocks();
          });
      };

  const editStock = (stock) => {

    setEditingId(stock.id);

    setStockName(stock.stockName);
    setTicker(stock.ticker);
    setBuyPrice(stock.buyPrice);
    setCurrentPrice(stock.currentPrice);
    setQuantity(stock.quantity);
  };

  const updateStock = () => {

    const request = {
      stockName,
      ticker: ticker,
      buyPrice: Number(buyPrice),
      currentPrice: Number(currentPrice),
      quantity: Number(quantity)
    };

    axios
      .put(
        `http://localhost:8080/api/stocks/${editingId}`,
        request
      )
      .then(() => {

        setEditingId(null);

        setStockName("");
        setTicker("");
        setBuyPrice("");
        setCurrentPrice("");
        setQuantity("");

        loadStocks();
      });
  };

  const searchStock = () => {

    const memberId =
      localStorage.getItem("memberId");

    axios
      .get(
        `http://localhost:8080/api/stocks/search?memberId=${memberId}&keyword=${keyword}`
      )
      .then((response) => {
        setStocks(response.data);
      });
  };

  const sortByProfitRate = () => {

    const memberId =
      localStorage.getItem("memberId");

    axios
      .get(
        `http://localhost:8080/api/stocks/sort/profit-rate/${memberId}`
      )
      .then((response) => {
        setStocks(response.data);
      });
  };

  const loadCurrentPrice = () => {

    axios
      .get(
        `http://localhost:8080/api/stocks/price/${ticker}`
      )
      .then((response) => {

        setCurrentPrice(
          response.data
        );

      });
  };

  const loadTicker = () => {

    axios
      .get(
        `http://localhost:8080/api/stocks/ticker?stockName=${stockName}`
      )
      .then((response) => {

        setTicker(response.data);

        return axios.get(
          `http://localhost:8080/api/stocks/price/${response.data}`
        );

      })
      .then((response) => {

        setCurrentPrice(
          response.data
        );

      })
      .catch(() => {

        alert("등록되지 않은 종목입니다.");

      });
  };

  const addStock = () => {

      const request = {
        stockName: stockName,
        ticker: ticker,
        buyPrice: Number(buyPrice),
        currentPrice: Number(currentPrice),
        quantity: Number(quantity)
      };

      axios
        const memberId =
          localStorage.getItem("memberId");

        axios.post(
          `http://localhost:8080/api/stocks/${memberId}`,
          request
        )
        .then(() => {

          setStockName("");
          setTicker("");
          setBuyPrice("");
          setCurrentPrice("");
          setQuantity("");

          loadStocks();
        });
    };

  const signup = () => {

    axios.post(
      "http://localhost:8080/api/members",
      {
        email,
        password,
        name
      }
    )
    .then(() => {

      alert("회원가입 성공");

      setEmail("");
      setPassword("");
      setName("");
    });
  };

  const login = () => {

    axios.post(
      "http://localhost:8080/api/members/login",
      {
        email: loginEmail,
        password: loginPassword
      }
    )
    .then((response) => {

        console.log(response.data);

      localStorage.setItem(
        "memberId",
        response.data.id
      );

      localStorage.setItem(
        "memberName",
        response.data.name
      );

      setMemberName(
        response.data.name
      );

      alert("로그인 성공");

      window.location.reload();
    });
  };
  const logout = () => {

    localStorage.removeItem("memberId");
    localStorage.removeItem("memberName");

    alert("로그아웃 되었습니다.");

    window.location.reload();
  };

  const totalInvestment = stocks.reduce(
    (sum, stock) => sum + stock.investment,
    0
  );

  const totalCurrentValue = stocks.reduce(
    (sum, stock) => sum + stock.currentValue,
    0
  );

  const totalProfit = stocks.reduce(
    (sum, stock) => sum + stock.profit,
    0
  );

  const topStocks = [...stocks]
    .sort(
      (a, b) =>
        b.profitRate - a.profitRate
    )
    .slice(0, 3);

  const chartData = stocks.map((stock) => ({
    name: stock.stockName,
    value: stock.currentValue
  }));

  const profitChartData = stocks.map((stock) => ({
    name: stock.stockName,
    profit: stock.profit
  }));

  return (
    <div style={{ padding: "20px" }}>

      <h1
        style={{
          textAlign: "center",
          marginBottom: "30px",
          fontSize: "42px"
        }}
      >
        📊 Stock Portfolio Manager
      </h1>

      {memberName ? (
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            gap: "10px",
            marginBottom: "20px"
          }}
        >
          <h3>
            현재 로그인 : {memberName}
          </h3>

          <button
            onClick={logout}
            style={{
              padding: "5px 10px",
              height: "35px",
              marginTop: "15px"
            }}
          >
            로그아웃
          </button>
        </div>
      ) : (
        <h3 style={{ textAlign: "center" }}>
          로그인되지 않음
        </h3>
      )}

      {!memberId && (
        <>
          <hr />

          <h2>회원가입</h2>

          <input
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            placeholder="비밀번호"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <input
            placeholder="이름"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

          <button onClick={signup}>
            회원가입
          </button>

          <hr />

          <h2>로그인</h2>

          <input
            placeholder="이메일"
            value={loginEmail}
            onChange={(e) => setLoginEmail(e.target.value)}
          />

          <input
            type="password"
            placeholder="비밀번호"
            value={loginPassword}
            onChange={(e) => setLoginPassword(e.target.value)}
          />

          <button onClick={login}>
            로그인
          </button>

          <hr />
        </>
      )}

      <div style={{
        display: "flex",
        justifyContent:"center",
        gap: "20px",
        marginBottom: "20px"
      }}>

        <div
          style={{
            border: "1px solid #ddd",
            borderRadius: "10px",
            padding: "20px",
            minWidth: "200px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
          }}
        >
          <h3>💰 총 투자금액</h3>
          <p style={{ fontSize: "24px", fontWeight: "bold" }}>
            {totalInvestment.toLocaleString()}원
          </p>
        </div>

        <div
          style={{
            border: "1px solid #ddd",
            borderRadius: "10px",
            padding: "20px",
            minWidth: "200px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
          }}
        >
          <h3>📈 총 평가금액</h3>
          <p style={{ fontSize: "24px", fontWeight: "bold" }}>
            {totalCurrentValue.toLocaleString()}원
          </p>
        </div>

        <div
          style={{
            border: "1px solid #ddd",
            borderRadius: "10px",
            padding: "20px",
            minWidth: "200px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
          }}
        >
          <h3>🔥 총 손익</h3>
          <p
            style={{
              fontSize: "24px",
              fontWeight: "bold",
              color: totalProfit >= 0 ? "green" : "red"
            }}
          >
            {totalProfit.toLocaleString()}원
          </p>
        </div>

      </div>

      <div
        style={{
          textAlign: "center",
          marginTop: "30px"
        }}
      >

      <div
        style={{
          marginTop: "30px",
          marginBottom: "30px"
        }}
      >
        <h2>🏆 수익률 TOP3</h2>

        <div
          style={{
            display: "flex",
            gap: "20px",
            justifyContent: "center"
          }}
        >
          {topStocks.map((stock, index) => (
            <div
              key={stock.id}
              style={{
                border: "1px solid #ddd",
                borderRadius: "10px",
                padding: "20px",
                minWidth: "180px",
                boxShadow:
                  "0 2px 8px rgba(0,0,0,0.1)"
              }}
            >
              <h3>
                {index === 0 && "🥇"}
                {index === 1 && "🥈"}
                {index === 2 && "🥉"}
              </h3>

              <h4>{stock.stockName}</h4>

              <p
                style={{
                  color:
                    stock.profitRate >= 0
                      ? "green"
                      : "red",
                  fontWeight: "bold"
                }}
              >
                {stock.profitRate.toFixed(2)}%
              </p>
            </div>
          ))}
        </div>
      </div>
        <h2>포트폴리오 비중</h2>

        <PieChart
          width={1100}
          height={350}
        >
        <Pie
          data={chartData}
          cx="50%"
          cy="50%"
          outerRadius={100}
          dataKey="value"
          label={({ name, percent }) =>
            `${name} ${(percent * 100).toFixed(0)}%`
          }
        >
          {chartData.map((entry, index) => (
            <Cell
              key={index}
              fill={COLORS[index % COLORS.length]}
            />
          ))}
        </Pie>

        <Tooltip />
        <Legend />
      </PieChart>

      <h2 style={{ marginTop: "40px" }}>
        종목별 손익
      </h2>

      <BarChart
        width={1100}
        height={350}
        data={profitChartData}
      >
        <CartesianGrid strokeDasharray="3 3" />

        <XAxis dataKey="name" />

        <YAxis />

        <Tooltip />

        <Bar
          dataKey="profit"
          fill="#3b82f6"
        />
      </BarChart>

      </div>

      <div
        style={{
          textAlign: "center",
          marginTop: "20px"
        }}
      >

                <input
                  placeholder="종목 검색"
                  value={keyword}
                  onChange={(e) => setKeyword(e.target.value)}
                />

                <button
                  style={{
                    padding: "8px 15px",
                    marginLeft: "5px"
                  }}
                  onClick={searchStock}
                >
                  검색
                </button>

               <button
                 style={{
                   padding: "8px 15px",
                   marginLeft: "5px"
                 }}
                 onClick={loadStocks}
               >
                  전체보기
                </button>

                <button
                  style={{
                    padding: "8px 15px",
                    marginLeft: "5px"
                  }}
                  onClick={sortByProfitRate}
                >
                  수익률순 정렬
                </button>

              </div>

      {memberId ? (

        <div>
          <input
            style={{
              padding: "10px",
              margin: "5px"
            }}
            placeholder="종목명"
            value={stockName}
            onChange={(e) => setStockName(e.target.value)}
            onBlur={loadTicker}
          />

          <input
            style={{
              padding: "10px",
              margin: "5px"
            }}
            placeholder="매수가"
            value={buyPrice}
            onChange={(e) => setBuyPrice(e.target.value)}
          />

          <input
            style={{
              padding: "10px",
              margin: "5px"
            }}
            placeholder="현재가"
            value={currentPrice}
            readOnly
          />

          <input
            style={{
              padding: "10px",
              margin: "5px"
            }}
            placeholder="수량"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
          />

          <hr />

          {editingId ? (
            <button onClick={updateStock}>
              수정 완료
            </button>
          ) : (
            <button onClick={addStock}>
              종목 추가
            </button>
          )}
        </div>

      ) : (

        <h3
          style={{
            textAlign: "center",
            color: "gray",
            marginTop: "20px"
          }}
        >
          로그인 후 종목을 관리할 수 있습니다.
        </h3>

      )}

      <hr />

 <table
   style={{
     width: "100%",
     borderCollapse: "collapse",
     marginTop: "20px"
   }}
 >
   <thead
     style={{
       backgroundColor: "#1e293b",
       color: "white"
     }}
   >
     <tr>
       <th style={{ padding: "15px" }}>종목명</th>
       <th style={{ padding: "15px" }}>티커</th>
       <th style={{ padding: "15px" }}>매수가</th>
       <th style={{ padding: "15px" }}>현재가</th>
       <th style={{ padding: "15px" }}>수량</th>
       <th style={{ padding: "15px" }}>투자금액</th>
       <th style={{ padding: "15px" }}>평가금액</th>
       <th style={{ padding: "15px" }}>손익</th>
       <th style={{ padding: "15px" }}>수익률</th>
       <th style={{ padding: "15px" }}>관리</th>
     </tr>
   </thead>

   <tbody>
     {stocks.map((stock) => (
       <tr
         key={stock.id}
         style={{
           borderBottom: "1px solid #e5e7eb"
         }}
       >
         <td style={{ padding: "15px",
                          textAlign: "center" }}>
           {stock.stockName}
         </td>
         <td style={{ padding: "15px",
                                   textAlign: "center" }}>
                    {stock.ticker}
                  </td>
         <td style={{ padding: "15px",
                          textAlign: "center" }}>
           {stock.buyPrice}
         </td>
         <td style={{ padding: "15px",
                          textAlign: "center" }}>
           {stock.currentPrice}
         </td>
         <td style={{ padding: "15px",
                          textAlign: "center" }}>
           {stock.quantity}
         </td>
         <td style={{ padding: "15px",
                          textAlign: "center" }}>
           {stock.investment}
         </td>
         <td style={{ padding: "15px",
                          textAlign: "center" }}>
           {stock.currentValue}
         </td>
         <td
           style={{
             color: stock.profit >= 0 ? "green" : "red"
           }}
         >
           {stock.profit.toLocaleString()}
         </td>
         <td
           style={{
             color: stock.profitRate >= 0 ? "green" : "red"
           }}
         >
           {stock.profitRate?.toFixed(2)}%
         </td>

         <td>

           {memberId && (
             <>
               <button
                 style={{
                   marginRight: "5px",
                   padding: "5px 10px"
                 }}
                 onClick={() => editStock(stock)}
               >
                 수정
               </button>

               <button
                 style={{
                   padding: "5px 10px"
                 }}
                 onClick={() => deleteStock(stock.id)}
               >
                 삭제
               </button>
             </>
           )}

         </td>

       </tr>
     ))}
   </tbody>
 </table>


    </div>
  );
}

export default App;