import {FC} from "react";

type LoginProps={
    user: any;
    handleSignOut: (event?: Event) => void;
}

const Login: FC<LoginProps> = ({user, handleSignOut}) => {

return (
    <div>
        <div id="signInDiv"></div>
        {user ? (
            <div>
                <h1>Welcome {user.name}</h1>
                <img src={user.picture} alt={'your picture'}></img>
                <button onClick={() => {
                    handleSignOut()
                }}>Log Out
                </button>
            </div>
        ) : null}
    </div>
)

}

export default Login;