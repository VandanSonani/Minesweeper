import './Layout.css'
import { FC, ReactNode } from "react";
import { Link } from "react-router-dom";

type LayoutProps = {
    children: ReactNode;
};

type navItem = {
    text: string;
    path: string;
}

const navItems: navItem[] = [
    { text: "Home", path: "/" },
    { text: "Select Game Mode", path: "/selectgamemode" },
    { text: "Daily Sweep", path: "/dailysweep" },
    { text: "Campaign", path: "/campaign" },
    { text: "Practice", path: "/practice" }
];


const Layout: FC<LayoutProps> = ({ children }) => {
    return (
        <div className="layout">
            <nav className={'nav-bar'}>

                {navItems.map((item, index) => (
                    <div key={index}>
                        <Link to={item.path}>
                            <div className={'nav-item'}>{item.text}</div>
                        </Link>
                    </div>
                ))}

            </nav>
            <main className="content">
                {children}
            </main>
        </div>
    );
};

export default Layout;