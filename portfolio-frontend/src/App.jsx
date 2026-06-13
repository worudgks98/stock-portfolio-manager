import { useEffect, useState } from "react";
import axios from "axios";

function App() {

  const [stocks, setStocks] = useState([]);

  const [stockName, setStockName] = useState("");
  const [buyPrice, setBuyPrice] = useState("");
  const [currentPrice, setCurrentPrice] = useState("");
  const [quantity, setQuantity] = useState("");

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

  return (
    <div style={{ padding: "20px" }}>

      <h1>Stock Portfolio Manager</h1>

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

        <button onClick={addStock}>
          종목 추가
        </button>
      </div>

      <hr />

      <ul>
        {stocks.map((stock) => (
          <li key={stock.id}>
            {stock.stockName}
          </li>
        ))}
      </ul>

    </div>
  );
}

export default App;