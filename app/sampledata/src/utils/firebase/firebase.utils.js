import { initializeApp } from "firebase/app";
import {
  getAuth,
  signInWithRedirect,
  signInWithPopup,
  GoogleAuthProvider,
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  signOut,
  onAuthStateChanged
} from "firebase/auth";

import { getFirestore, doc, getDoc, setDoc } from "firebase/firestore";
const firebaseConfig = {
  apiKey: "AIzaSyC2j_kpViwNiZJFCV90XicP3OjFkgXf-Xc",
  authDomain: "crown-clothing-db-6ecd3.firebaseapp.com",
  projectId: "crown-clothing-db-6ecd3",
  storageBucket: "crown-clothing-db-6ecd3.appspot.com",
  messagingSenderId: "973904704391",
  appId: "1:973904704391:web:fef607b0781c1c6e1a9922",
};

// Initialize Firebase
const firebaseApp = initializeApp(firebaseConfig);

const googleProvider = new GoogleAuthProvider();

googleProvider.setCustomParameters({
  prompt: "select_account",
});

export const auth = getAuth();

export const signInWithGooglePopUp = () => signInWithPopup(auth, googleProvider);
export const signInWithGoogleRedirect = ()=>signInWithRedirect(auth,googleProvider);

export const db = getFirestore();
export const createUserDocumentWithAuth = async (userAuth,additionalInformation={}) => {

  if(!userAuth) return;
  const userDocRef = doc(db, "users", userAuth.uid);
  console.log(userDocRef);

  const userSnapshot = await getDoc(userDocRef);
  console.log(userSnapshot);
  console.log(userSnapshot.exists());

  if (!userSnapshot.exists()) {
    const { displayName, email } = userAuth;

    const createdAt = new Date();

    try {
      await setDoc(userDocRef, {
        displayName,
        email,
        createdAt,
        ...additionalInformation
      });
    } catch (error) {
      console.log("error creating the user", error.message);
    }
  }
  return userDocRef;
};

export const createAuthUserWithEmailAndPassword=async(email,password)=>
{
  if(!email || !password) return;

  return await createUserWithEmailAndPassword(auth,email,password)
}

export const signInAuthUserWithEmailAndPassword=async(email,password)=>
  {
    if(!email || !password) return;
  
    return await signInWithEmailAndPassword(auth,email,password)
  }


  export const signOutUser=async()=>await signOut(auth);

  export const onAuthStateChangedListener=(callback)=>
  {
    onAuthStateChanged(auth,callback)
  }

