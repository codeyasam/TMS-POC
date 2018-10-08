import AddApplicationForm from '../../ui/applications/AddApplicationForm'
import { addApplicationRequest } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    
})

const mapDispatchToProps = dispatch => ({
    onAddApplication(application) {
        dispatch(addApplicationRequest(application))
    }
}) 

export default connect(mapStateToProps, mapDispatchToProps)(AddApplicationForm)