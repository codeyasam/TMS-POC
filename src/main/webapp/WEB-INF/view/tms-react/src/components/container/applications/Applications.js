import Applications from '../../ui/applications/Applications'
import { fetchApplications, resetSuccessfullyAddApplication } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    applicationList: state.application.applicationList,
    isSuccessfullyAdded: state.application.addApplication.successfullyAddedApplication    
})

const mapDispatchToProps = dispatch => ({
    onFetchApplications() {
        dispatch(fetchApplications())
    }, 
    onPromptModalClose() {
        dispatch(resetSuccessfullyAddApplication())        
        //also reset successfullyEditApplication
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Applications)