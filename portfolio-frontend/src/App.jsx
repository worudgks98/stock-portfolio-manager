import { useEffect, useState } from "react";
import axios from "axios";

function App() {

  const [stocks, setStocks] = useState([]);

  useEffect(() => {

    axios
      .get("http://localhost:8080/api/stocks/1")
      .then((response) => {
        setStocks(response.data);
      })
      .catch((error) => {
        console.error(error);
      });

  }, []);

  return (
    <div>
      <h1>Stock Portfolio Manager</h1>

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