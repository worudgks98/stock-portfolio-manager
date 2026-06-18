import { useEffect, useState } from "react";
import axios from "axios";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  Legend
} from "recharts";

function App() {

  const [stocks, setStocks] = useState([]);

  const [stockName, setStockName] = useState("");
  const [buyPrice, setBuyPrice] = useState("");
  const [currentPrice, setCurrentPrice] = useState("");
  const [quantity, setQuantity] = useState("");
  const [editingId, setEditingId] = useState(null);

  const loadStocks = () => {
    axios
      .get("http://localhost:8080/api/stocks/1")
      .then((response) => {
        setStocks(response.data);
      });
  };

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
    setBuyPrice(stock.buyPrice);
    setCurrentPrice(stock.currentPrice);
    setQuantity(stock.quantity);
  };

  const updateStock = () => {

    const request = {
      stockName,
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
        setBuyPrice("");
        setCurrentPrice("");
        setQuantity("");

        loadStocks();
      });
  };

  const addStock = () => {

    const request = {
      stockName: stockName,
      buyPrice: Number(buyPrice),
      currentPrice: Number(currentPrice),
      quantity: Number(quantity)
    };

    axios
      .post("http://localhost:8080/api/stocks/1", request)
      .then(() => {

        setStockName("");
        setBuyPrice("");
        setCurrentPrice("");
        setQuantity("");

        loadStocks();
      });
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

  const chartData = stocks.map((stock) => ({
    name: stock.stockName,
    value: stock.currentValue
  }));

  return (
    <div style={{ padding: "20px" }}>

      <h1>Stock Portfolio Manager</h1>

      <div style={{
        display: "flex",
        gap: "20px",
        marginBottom: "20px"
      }}>

        <div>
          <h3>총 투자금액</h3>
          <p>{totalInvestment.toLocaleString()}원</p>
        </div>

        <div>
          <h3>총 평가금액</h3>
          <p>{totalCurrentValue.toLocaleString()}원</p>
        </div>

        <div>
          <h3>총 손익</h3>
          <p>{totalProfit.toLocaleString()}원</p>
        </div>

      </div>

      <h2>포트폴리오 비중</h2>

      <PieChart width={500} height={300}>
        <Pie
          data={chartData}
          cx="50%"
          cy="50%"
          outerRadius={100}
          dataKey="value"
          label
        >
          {chartData.map((entry, index) => (
            <Cell key={index} />
          ))}
        </Pie>

        <Tooltip />
        <Legend />
      </PieChart>

      <div>
        <input
          placeholder="종목명"
          value={stockName}
          onChange={(e) => setStockName(e.target.value)}
        />

        <input
          placeholder="매수가"
          value={buyPrice}
          onChange={(e) => setBuyPrice(e.target.value)}
        />

        <input
          placeholder="현재가"
          value={currentPrice}
          onChange={(e) => setCurrentPrice(e.target.value)}
        />

        <input
          placeholder="수량"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
        />

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

      <hr />

      <table border="1">
        <thead>
          <tr>
            <th>종목명</th>
            <th>매수가</th>
            <th>현재가</th>
            <th>수량</th>
            <th>투자금액</th>
            <th>평가금액</th>
            <th>손익</th>
            <th>수익률</th>
            <th>관리</th>
          </tr>
        </thead>

        <tbody>
          {stocks.map((stock) => (
            <tr key={stock.id}>
              <td>{stock.stockName}</td>
              <td>{stock.buyPrice}</td>
              <td>{stock.currentPrice}</td>
              <td>{stock.quantity}</td>
              <td>{stock.investment}</td>
              <td>{stock.currentValue}</td>
              <td>{stock.profit}</td>
              <td>{stock.profitRate?.toFixed(2)}%</td>
              <td>
                <td>

                  <button
                    onClick={() => editStock(stock)}
                  >
                    수정
                  </button>

                  <button
                    onClick={() => deleteStock(stock.id)}
                  >
                    삭제
                  </button>

                </td>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

    </div>
  );
}

export default App;