import ReactDOM from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { UserProvider } from "./UserContext";


ReactDOM.createRoot(document.getElementById('root')).render(
    <UserProvider>
        <App />
    </UserProvider>
)